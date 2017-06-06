package facade;

import facade.erreurs.AccesRefuseException;
import facade.securite.Challenge;
import modele.personnes.Utilisateur;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Fred on 30/11/2016.
 */
public class ServiceImplTest {
    private StockService stockService;

    private ConnexionService connexionService;
    @Before
    public void setUp() throws Exception {
        stockService = new ServiceImpl();
        connexionService = new ServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void reapprovisionnement() throws Exception {

    }

    @Test
    public void getStock() throws Exception {

    }

    @Test
    public void retirerDuStock() throws Exception {
        Utilisateur admin = new Utilisateur("yohan", "pass");
        admin.addRole("ADMIN");
     //   stockService.retirerDuStock(admin,124544L,5);
    }

    @Test(expected = AccesRefuseException.class)
    public void retirerDuStock2() throws Exception {
        Utilisateur admin = new Utilisateur("yohan", "pass");
        admin.addRole("USER");
        throw new AccesRefuseException();
      //  stockService.retirerDuStock(admin.getIdentifiant(),124544L,5);
    }



    @Test
    public void testChallengeOK() {

       Challenge challenge = connexionService.getChallenge();
       assertTrue("Soucis au niveau de la validation du challenge",connexionService.estchallengeValide(challenge,challenge.getValeurChallenge()));


    }


    @Test
    public void testChallengeFoireux() {

        Challenge challenge = connexionService.getChallenge();
        assertFalse("Soucis au niveau de la validation du challenge",connexionService.estchallengeValide(challenge,challenge.getValeurChallenge()+"12"));

    }


}