package services.Impl;

import lib.Roles;
import lib.TypeConfiguration;
import models.Statistics;
import models.User;
import repository.ConfigurationDAO;
import repository.StatisticsDAO;
import repository.UserDAO;
import services.StatisticService;
import soapmodels.statistics.StatSoap;
import soapmodels.statistics.StatisticsSoapList;
import token.TokenProvider;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;

@WebService(endpointInterface = "services.StatisticService")
public class StatisticServiceImpl implements StatisticService {

    @EJB
    StatisticsDAO statDAO;

    @EJB
    ConfigurationDAO confDAO;

    @EJB
    UserDAO userDAO;

    @PostConstruct
    private void init(){
        if (statDAO == null) {
            try {
                statDAO = (StatisticsDAO) new InitialContext().lookup("mappedStatisticsDAO#repository.StatisticsDAO");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        if (confDAO == null) {
            try {
                confDAO = (ConfigurationDAO) new InitialContext().lookup("mappedConfigurationDAO#repository.ConfigurationDAO");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        if (userDAO == null) {
            try {
                userDAO = (UserDAO) new InitialContext().lookup("mappedUserDAO#repository.UserDAO");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public StatisticsSoapList getStatistics(String token) {
        StatisticsSoapList statResult = new StatisticsSoapList();
        statResult.setMessage("Failure get statistics!");
        statResult.setSuccess(false);
        if (null != token && !token.isEmpty() && TokenProvider.isValidToken(token)){
            String adminPN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(adminPN);
            if (null != admin && admin.getRole() == Roles.ADMIN){
                statResult.setStatistics(new ArrayList<StatSoap>());
                for (Statistics stat : statDAO.findAll()) {
                    System.out.println(stat.getDescription());
                    statResult.getStatistics().add(new StatSoap(stat.getName(),stat.getDescription() != null ? stat.getDescription().toString() : "0"));
                }
                statResult.setUpdateTime(confDAO.findById(TypeConfiguration.TIMER.getType()).getDescription());
                statResult.setMessage("Success get statistics!");
                statResult.setSuccess(true);
            }
        }
        return statResult;
    }
}
