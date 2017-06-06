package facade;

import facade.erreurs.ProduitEncoreEnStockException;
import facade.securite.HasRole;
import modele.produit.Produit;
import modele.produit.ProduitQuantite;

import java.time.LocalDate;
import java.util.Collection;

public interface StockService {

    /**
     *
     * @param idUtilisateur : identifiant de l'utilisateur demandant le réapproxisionnement
     * @param idProduit : identifiant du produit ciblé
     * @param quantite : nouvelle quantité à ajouter au stock
     *
     * Si l'utilisateur a le rôle approprié alors il pourra réapprovisionner le stock
     */
    @HasRole("RESPONSABLESTOCK")
    public void reapprovisionnement(long idUtilisateur, long idProduit, int quantite);


    /**
     *
     * @param idUtilisateur : identifiant de l'utilisateur demandant le stock

     * @param idProduit : identifiant du produit pour lequel on veut connaître le stock
     * @return : la quantité présente en stock si l'utilisateur a le droit d'utiliser cette fonctionnalité
     */

    @HasRole("RESPONSABLESTOCK")
    public int getStock(long idUtilisateur, long idProduit);


    /**
     *
     * Méthode pour réajuster le stock si l'utilisateur (idUtilisateur) est ADMIN.
     */
    @HasRole("ADMIN")
    public void retirerDuStock(long idUtilisateur, long idProduit, int quantite);


    /**
     *
     * @param idUtilisateur : identifiant de l'utilisateur demandant la suppression du catalogue
     * @param idProduit : produit visé par la suppression
     * @return le produit supprimé si l'utilisateur avait le droit de le faire
     */
    @HasRole("RESPONSABLESTOCK")
    public Produit supprimerDuCatalogue(long idUtilisateur,long idProduit) throws ProduitEncoreEnStockException;


    /**
     *
     * Permet d'ajouter un livre dans le catalogue si l'utilisateur (idUtilisateur) a le rôle attendu.
     * L'identifiant du nouveau livre ajouté est retourné
     */
    @HasRole("RESPONSABLESTOCK")
    public long ajouterLivre(long idUtilisateur,String libelle, double prix, String isbn, int nbPages, String editeur, LocalDate edition);


    /**
     *
     * Permet d'ajouter un CD dans le catalogue si l'utilisateur (idUtilisateur) a le rôle attendu.
     * L'identifiant du nouveau CD ajouté est retourné
     */

    @HasRole("RESPONSABLESTOCK")
    public long ajouterCD(long idUtilisateur,String libelle, double prix, String chanteur);


    /**
     *
     * @return un tableau des types de produits gérés dans le catalogue
     */
    public String[] getTypeProduits();


    /**
     *
     * @param idProduit : identifiant du produit à récupérer
     * @return le produit correspondant à l'identifiant
     */
    public Produit getProduitById(long idProduit);


    /**
     * Permet à l'utilisateur (idUtilisateur) d'avoir la liste des stocks non vides pour
     * si ce dernier a les droits nécessaires
     */

    @HasRole("RESPONSABLESTOCK")
    public Collection<ProduitQuantite> getStockNonVides(long idUtilisateur);


    /**
     * Permet à l'utilisateur (idUtilisateur) d'avoir la liste des stocks vides pour
     * si ce dernier a les droits nécessaires
     */

    @HasRole("RESPONSABLESTOCK")
    public Collection<ProduitQuantite> getStockVides(long idUtilisateur);




}
