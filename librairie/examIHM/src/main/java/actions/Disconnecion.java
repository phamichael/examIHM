package actions;

import com.opensymphony.xwork2.ActionSupport;
import facade.ConnexionService;
import facade.ServiceImpl;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by root on 6/3/17.
 */
public class Disconnecion extends ActionSupport implements ApplicationAware, SessionAware
{

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
        service.deconnexion((Long) session.get("id"));
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