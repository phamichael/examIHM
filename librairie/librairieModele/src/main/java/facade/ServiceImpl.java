package facade;


import facade.erreurs.*;
import facade.securite.Challenge;
import modele.personnes.Client;
import modele.personnes.Personne;
import modele.personnes.Utilisateur;
import modele.produit.CD;
import modele.produit.Livre;
import modele.produit.Produit;
import modele.produit.ProduitQuantite;
import modele.stock.StockProduits;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static facade.securite.AccessControl.checkAnnotationAccess;

public class ServiceImpl implements StockService, ConnexionService {
    private static StockProduits stockProduits = new StockProduits();
    private static Map<Long,Produit> catalogue = new HashMap<>();
    private static Map<Long,Client> clients = new HashMap<>();
    private static Map<Long,Utilisateur> utilisateurs = new HashMap<>();

    private static Collection<Long> idsConnectes = new ArrayList<>();

    public static String ADMIN = "ADMIN";
    public static String VENDEUR = "VENDEUR";
    public static String RESPONSABLESTOCK = "RESPONSABLESTOCK";


    private static Map<String,Challenge> challenges = new HashMap<>();



    public ServiceImpl() {
        Utilisateur admin = new Utilisateur("admin","admin");
        Utilisateur vendeur = new Utilisateur("vendeur","vendeur");
        Utilisateur stock1 = new Utilisateur("stock","stock");
        try {
            admin.addRole(ADMIN);
            admin.addRole(VENDEUR);
            admin.addRole(RESPONSABLESTOCK);
            vendeur.addRole(VENDEUR);
            stock1.addRole(RESPONSABLESTOCK);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }





        utilisateurs.put(admin.getIdentifiant(),admin);
        utilisateurs.put(vendeur.getIdentifiant(),vendeur);
        utilisateurs.put(stock1.getIdentifiant(),stock1);

        // ajout de quelques clients
        Client yohan = new Client("boichut","yohan","orleans",45100,"ORLEANS",LocalDate.now());
        clients.put(yohan.getIdentifiant(),yohan);
        Client fred = new Client("moal","frederic","orleans",45100,"ORLEANS",LocalDate.now());
        clients.put(fred.getIdentifiant(),fred);

        Livre jfx = new Livre("JavaFX", 45.99, "123456-45", 512, "Oracle press", LocalDate.parse("2014-01-03"));
        catalogue.put(jfx.getId(),jfx);
        Livre jfx2 = new Livre("JavaFX2", 48.99, "123456-33", 542, "Oracle press", LocalDate.parse("2016-01-03"));
        catalogue.put(jfx2.getId(),jfx2);
        CD cd = new CD("Greatest Hits", 12.99, "Leonard Cohen");
        catalogue.put(cd.getId(),cd);
        stockProduits.addStock(jfx, 0);
        stockProduits.addStock(jfx2, 9);
        stockProduits.addStock(cd, 2);

        //yohan.addAchats(new ProduitQuantite(jfx,1));
        //fred.addAchats(new ProduitQuantite(jfx,1));
        fred.addAchats(new ProduitQuantite(jfx2,2));
        fred.addAchats(new ProduitQuantite(cd,1));
    }



    @Override
    public void reapprovisionnement(long idUtilisateur, long idProduit, int quantite) {
        if (idsConnectes.contains(idUtilisateur)) {
            checkAnnotationAccess(new Object() {
            }.getClass().getEnclosingMethod(), utilisateurs.get(idUtilisateur));
            stockProduits.addStock(catalogue.get(idProduit), quantite);
        }
        else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public int getStock(long idUtilisateur, long idProduit){
        if (idsConnectes.contains(idUtilisateur)) {
            checkAnnotationAccess(new Object(){}.getClass().getEnclosingMethod(), utilisateurs.get(idUtilisateur));
            return stockProduits.getStock(catalogue.get(idProduit));
        }
        else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public void retirerDuStock(long idUtilisateur, long idProduit, int quantite) {
        if (idsConnectes.contains(idUtilisateur)) {

            checkAnnotationAccess(new Object() {
            }.getClass().getEnclosingMethod(), utilisateurs.get(idUtilisateur));
            stockProduits.addStock(catalogue.get(idProduit), -quantite);
        }
        else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public Produit supprimerDuCatalogue(long idUtilisateur, long idProduit) throws ProduitEncoreEnStockException {
        if (idsConnectes.contains(idUtilisateur)) {
            checkAnnotationAccess(new Object() {
            }.getClass().getEnclosingMethod(), utilisateurs.get(idUtilisateur));
            if (getStock(idUtilisateur,idProduit) == 0) {
                Produit produit = getProduitById(idProduit);
                catalogue.remove(idProduit);
                return produit;
            }
            else {
                throw new ProduitEncoreEnStockException();
            }
        }
        else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public long ajouterLivre(long idUtilisateur, String libelle, double prix, String isbn, int nbPages, String editeur, LocalDate edition) {
        if (idsConnectes.contains(idUtilisateur)) {
            checkAnnotationAccess(new Object() {
            }.getClass().getEnclosingMethod(), utilisateurs.get(idUtilisateur));
            Livre nouveauLivre = new Livre(libelle, prix, isbn, nbPages, editeur, edition);

            catalogue.put(nouveauLivre.getId(),nouveauLivre);

            return nouveauLivre.getId();
        }
        else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public long ajouterCD(long idUtilisateur, String libelle, double prix, String chanteur) {
        if (idsConnectes.contains(idUtilisateur)) {
            checkAnnotationAccess(new Object() {
            }.getClass().getEnclosingMethod(), utilisateurs.get(idUtilisateur));
            CD nouveauCD = new CD(libelle, prix,chanteur);

            catalogue.put(nouveauCD.getId(),nouveauCD);
            return nouveauCD.getId();
        }
        else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public String[] getTypeProduits() {
        return Produit.TYPESPRODUITS;
    }

    @Override
    public Produit getProduitById(long idProduit) {
        return catalogue.get(idProduit);
    }

    @Override
    public Collection<ProduitQuantite> getStockNonVides(long idUtilisateur) {
        if (idsConnectes.contains(idUtilisateur)) {
            checkAnnotationAccess(new Object() {
            }.getClass().getEnclosingMethod(), utilisateurs.get(idUtilisateur));
            List<ProduitQuantite> resultat = new ArrayList<>();
            for (long idProduits : catalogue.keySet()) {
                if (stockProduits.getStock(catalogue.get(idProduits)) > 0) {
                    resultat.add(stockProduits.getProduitQuantite(catalogue.get(idProduits)));

                    System.out.println(stockProduits.getProduitQuantite(catalogue.get(idProduits)).getProduit().getId()
                    + " " + stockProduits.getProduitQuantite(catalogue.get(idProduits)).getProduit().getLibelle());
                }
            }
            return resultat;
        }
        else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public Collection<ProduitQuantite> getStockVides(long idUtilisateur) {
        if (idsConnectes.contains(idUtilisateur)) {
            checkAnnotationAccess(new Object() {
            }.getClass().getEnclosingMethod(), utilisateurs.get(idUtilisateur));
            List<ProduitQuantite> resultat = new ArrayList<>();
            for (long idProduits : catalogue.keySet()) {
                if (stockProduits.getStock(catalogue.get(idProduits))==0) {
                    resultat.add(stockProduits.getProduitQuantite(catalogue.get(idProduits)));
                }
            }
            return resultat;
        }
        else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public Utilisateur connexion(String pseudo, String mdp) throws CoupleUtilisateurMDPInconnuException {
        for (Utilisateur u : utilisateurs.values()) {
            if ((pseudo.equals(u.getNom())) && (mdp.equals(u.getMdp()))) {
                idsConnectes.add(u.getIdentifiant());
                return u;
            }
        }
        throw new CoupleUtilisateurMDPInconnuException();
    }

    @Override
    public void deconnexion(long idUtilisateur) {
        if (idsConnectes.contains(idUtilisateur)) {
            idsConnectes.remove(idUtilisateur);
        } else {
            throw new IndividuNonConnecteException();
        }
    }

    @Override
    public boolean estUnUtilisateurConnu(String pseudo) {
        for (Utilisateur u : utilisateurs.values()) {
            if ((pseudo.equals(u.getNom()))) {
                return true;
            }
        }
        return false;
    }





    @Override
    public boolean estUnAdmin(long idUtilisateur) {
        return utilisateurs.get(idUtilisateur).hasRole(ADMIN);
    }

    @Override
    public boolean estUnVendeur(long idUtilisateur) {
        return utilisateurs.get(idUtilisateur).hasRole(VENDEUR);
    }

    @Override
    public boolean estResponsableDesStocks(long idUtilisateur) {
        return utilisateurs.get(idUtilisateur).hasRole(RESPONSABLESTOCK);
    }


    private String generationChallenge() {
       String resultat = "";
        for (int i=0;i<10;i++) {
            resultat+=(int) (Math.random() * 10);
        }
        return resultat;
    }

    @Override
    public Challenge getChallenge() {
        String cle = this.generationChallenge();
        String valeur = this.generationChallenge();
        Challenge challenge = new Challenge(cle,valeur);
        challenges.put(challenge.toString(),challenge);
        return challenge;
    }

    @Override
    public boolean estchallengeValide(Challenge challengePropose, String valeurProposee) {
        if (challenges.containsKey(challengePropose.toString())) {
            return challenges.get(challengePropose.toString()).chalengeReleve(valeurProposee);
        }
        return false;
    }
}
