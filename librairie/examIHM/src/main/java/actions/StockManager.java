package actions;

import com.opensymphony.xwork2.ActionSupport;
import facade.ConnexionService;
import facade.ServiceImpl;
import modele.produit.Produit;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by root on 6/3/17.
 */
public class StockManager extends ActionSupport implements ApplicationAware, SessionAware
{

    private String typeSelected;

    private String title;

    private String editor;

    private String year;

    private String month;

    private String day;

    private String isbnNumber;

    private String numberOfPages;

    private String singer;

    private String price;

    private String itemToDelete;

    private String itemSelected;

    private Produit item;

    private String quantity;

    private Map<String, Object> session;

    private ServiceImpl service;

    public void setApplication(Map<String, Object> map)
    {
        service = (ServiceImpl) map.get("service");
        if(service == null)
        {
            service = new ServiceImpl();
            map.put("service", service);
        }
    }

    @Override
    public String execute() throws Exception
    {
        return SUCCESS;
    }

    public String selectItemsType() throws Exception
    {
        return SUCCESS;
    }

    public String addItem() throws Exception
    {
        Long id = (Long) session.get("id");
        if(typeSelected.equals("LIVRE"))
        {
            LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            service.ajouterLivre(id, title, Double.parseDouble(price),
                    isbnNumber, Integer.parseInt(numberOfPages), editor, date);
        } else if(typeSelected.equals("CD"))
        {
            service.ajouterCD(id, title, Double.parseDouble(price), singer);
        }
        session.put("emptyStocks", service.getStockVides(id));
        return SUCCESS;
    }

    public String deleteItem() throws Exception
    {
        Long id = (Long) session.get("id");
        service.supprimerDuCatalogue(id, Long.parseLong(itemToDelete));
        session.put("emptyStocks", service.getStockVides(id));
        return SUCCESS;
    }

    public String selectItem() throws Exception
    {
        item = service.getProduitById((Long.parseLong(itemSelected)));
        return SUCCESS;
    }

    public String orderItem() throws Exception
    {
        Long id = (Long) session.get("id");
        service.retirerDuStock(id, Long.parseLong(itemSelected), Integer.parseInt(quantity));
        session.put("itemsInStock", service.getStockNonVides(id));
        return SUCCESS;
    }

    public String restockItem() throws Exception
    {
        Long id = (Long) session.get("id");
        service.reapprovisionnement(id, Long.parseLong(itemSelected), Integer.parseInt(quantity));
        session.put("emptyStocks", service.getStockVides(id));
        session.put("itemsInStock", service.getStockNonVides(id));
        return SUCCESS;
    }

    public String getTypeSelected() {
        return typeSelected;
    }

    public void setTypeSelected(String typeSelected) {
        this.typeSelected = typeSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemToDelete() {
        return itemToDelete;
    }

    public void setItemToDelete(String itemToDelete) {
        this.itemToDelete = itemToDelete;
    }

    public String getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(String itemSelected) {
        this.itemSelected = itemSelected;
    }

    public Produit getItem() {
        return item;
    }

    public void setItem(Produit item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}