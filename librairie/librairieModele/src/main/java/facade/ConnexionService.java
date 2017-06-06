package facade;

import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import facade.securite.Challenge;
import modele.personnes.Personne;

public interface ConnexionService {

    Personne connexion(String pseudo, String mdp) throws CoupleUtilisateurMDPInconnuException;


    public void deconnexion(long idUtilisateur);


    /**
     *
     * @param pseudo : pseudo d'un utilisateur potentiel
     * @return : true si le pseudo correspond au nom d'un utilisateur du SI.
     */
    boolean estUnUtilisateurConnu(String pseudo);

    /**
     *
     * @param idUtilisateur : identifiant de l'utilisateur
     * @return true si l'utilisateur est un administrateur et false sinon
     */

    boolean estUnAdmin(long idUtilisateur);

    /**
     *
     * @param idUtilisateur : identifiant de l'utilisateur
     * @return true si l'utilisateur est un vendeur et false sinon
     */
    boolean estUnVendeur(long idUtilisateur);

    /**
     *
     * @param idUtilisateur : identifiant de l'utilisateur
     * @return true si l'utilisateur est un responsable des stocks et false sinon
     */
    boolean estResponsableDesStocks(long idUtilisateur);


    /**
     *
     * @return un challenge créé par le serveur.
     */
    Challenge getChallenge();


    /**
     *
     * @param challengePropose : challenge précédemment proposé par le serveur
     * @param valeurProposee : la valeur saisie par l'utilisateur
     * @return true si la valeur saisie par l'utilisateur correspond à la valeur attendue et false sinon.
     */

    boolean estchallengeValide(Challenge challengePropose,String valeurProposee);


}
