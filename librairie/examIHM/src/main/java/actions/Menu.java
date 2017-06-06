package actions;

import com.opensymphony.xwork2.ActionSupport;
import facade.ServiceImpl;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by root on 6/3/17.
 */
public class Menu extends ActionSupport implements ApplicationAware, SessionAware
{

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

    public String seller() throws Exception
    {
        return SUCCESS;
    }

    public String admin() throws Exception
    {
        return SUCCESS;
    }

    public String stockManager() throws Exception
    {
        Long id = (Long) session.get("id");
        session.put("itemsTypes", service.getTypeProduits());
        session.put("emptyStocks", service.getStockVides(id));
        session.put("itemsInStock", service.getStockNonVides(id));
        return SUCCESS;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
