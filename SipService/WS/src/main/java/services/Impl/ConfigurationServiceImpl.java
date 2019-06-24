package services.Impl;

import models.Configuration;
import repository.ConfigurationDAO;
import services.ConfigurationService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

@WebService(endpointInterface = "services.ConfigurationService")
public class ConfigurationServiceImpl implements ConfigurationService {

    @EJB
    ConfigurationDAO confDAO;

    @PostConstruct
    private void init(){
        if (confDAO == null) {
            try {
                confDAO = (ConfigurationDAO) new InitialContext().lookup("mappedConfigurationDAO#repository.ConfigurationDAO");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Configuration> getAllConfigs() {
        return confDAO.findAll();
    }
}
