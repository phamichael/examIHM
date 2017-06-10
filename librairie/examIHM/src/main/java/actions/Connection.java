package actions;

import facade.ConnexionService;
import facade.ServiceImpl;
import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import facade.securite.Challenge;
import modele.personnes.Personne;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by root on 6/3/17.
 */
public class Connection extends ActionSupport implements ApplicationAware, SessionAware
{
    private String login;

    private String challengeInput;

    private String password;

    private Map<String, Object> session;

    private ConnexionService service;

    public void setApplication(Map<String, Object> map)
    {
        service = (ConnexionService) map.get("service");
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

    public String login() throws Exception
    {
        session.put("login", login);
        session.put("challenge", service.getChallenge());
        return SUCCESS;
    }

    public String challenge() throws Exception
    {
        if(service.estchallengeValide((Challenge) session.get("challenge"), challengeInput))
        {
            if(service.estUnUtilisateurConnu((String) session.get("login")))
                return SUCCESS;
        }
        return ERROR;
    }

    public String password() throws Exception
    {
        try {
            Personne user = service.connexion((String) session.get("login"), password);
            Long id = user.getIdentifiant();
            session.put("id", id);
            session.put("isSeller", service.estUnVendeur(id));
            session.put("isAdmin", service.estUnAdmin(id));
            session.put("isStockManager", service.estResponsableDesStocks(id));
            session.put("password", password);
        } catch(CoupleUtilisateurMDPInconnuException e)
        {
            return ERROR;
        }
        return SUCCESS;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getChallengeInput() {
        return challengeInput;
    }

    public void setChallengeInput(String challengeInput) {
        this.challengeInput = challengeInput;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}

