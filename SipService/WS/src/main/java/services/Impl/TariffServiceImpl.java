package services.Impl;

import lib.Roles;
import models.Tariff;
import models.User;
import repository.TariffDAO;
import repository.UserDAO;
import services.TariffService;
import soapmodels.tariff.TariffSoap;
import token.TokenProvider;
import validator.Validator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "services.TariffService")
public class TariffServiceImpl implements TariffService {

    @EJB
    TariffDAO tariffDAO;
    @EJB
    UserDAO userDAO;

    @PostConstruct
    private void init(){
        if (tariffDAO == null) {
            try {
                tariffDAO = (TariffDAO) new InitialContext().lookup("mappedTariffDAO#repository.TariffDAO");
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
    public TariffSoap getTariffs() {
        TariffSoap tariffSoap = new TariffSoap();
        tariffSoap.setTariffs(tariffDAO.findAll());
        tariffSoap.setMessage("Success get tariff!");
        tariffSoap.setSuccess(true);
        return tariffSoap;
    }

    @Override
    public TariffSoap addTariff(String token, Tariff tariff) {
        TariffSoap tariffSoap = new TariffSoap();
        tariffSoap.setMessage("Failure add new tariff! Token is not valid or data 'tariff' is not correct!");
        tariffSoap.setSuccess(false);
        if(null != token && null != tariff && !token.isEmpty() && Validator.isValidTariff(tariff) && TokenProvider.isValidToken(token)) {
            String adminPN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(adminPN);
            if (null != admin && admin.getRole() == Roles.ADMIN) {
                tariff.setCost(Double.parseDouble(String.format("%.2f", tariff.getCost()).replace(",",".")));
                tariffDAO.addAndUpdate(tariff);
                tariffSoap.setTariffs(tariffDAO.findAll());
                tariffSoap.setMessage("Success add new tariff!");
                tariffSoap.setSuccess(true);
            }else
                tariffSoap.setMessage("Failure add new tariff! Access insufficient access rights!");
        }
        return tariffSoap;
    }

    @Override
    public TariffSoap editTariff(String token, Tariff tariff) {
        TariffSoap tariffSoap = new TariffSoap();
        tariffSoap.setMessage("Failure edit tariff! Token is not valid or data 'tariff' is not correct!");
        tariffSoap.setSuccess(false);
        if(null != token && null != tariff && !token.isEmpty() && Validator.isValidTariff(tariff) && TokenProvider.isValidToken(token)) {
            String adminPN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(adminPN);
            if (null != admin && admin.getRole() == Roles.ADMIN) {
                Tariff findTarif = tariffDAO.findById(tariff.getName());
                if (null != findTarif){
                    findTarif.setCost(Double.parseDouble(String.format("%.2f", tariff.getCost()).replace(",",".")));
                    tariffDAO.addAndUpdate(tariff);
                    tariffSoap.setTariffs(tariffDAO.findAll());
                    tariffSoap.setMessage("Success edit tariff!");
                    tariffSoap.setSuccess(true);
                }else
                    tariffSoap.setMessage("Failure edit tariff! Tariff not found!");
            }else
                tariffSoap.setMessage("Failure edit tariff! Access insufficient access rights!");
        }
        return tariffSoap;
    }

    @Override
    public TariffSoap deleteTariffs(String token, ArrayList<String> tariffs) {
        TariffSoap tariffSoap = new TariffSoap();
        tariffSoap.setMessage("Failure delete tariff! Token is not valid!");
        tariffSoap.setSuccess(false);
        if (null != token && null != tariffs && !token.isEmpty() && tariffs.size() > 0 && TokenProvider.isValidToken(token)){
            String adminPN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(adminPN);
            if (null != admin && admin.getRole() == Roles.ADMIN) {
                for (String tName : tariffs) {
                    Tariff delCandidat = tariffDAO.findById(tName);
                    if (null != delCandidat)
                        tariffDAO.delete(delCandidat);
                }
                tariffSoap.setTariffs(tariffDAO.findAll());
                tariffSoap.setMessage("Success delete tariffs!");
                tariffSoap.setSuccess(true);
            }else
                tariffSoap.setMessage("Failure delete tariff! Access insufficient access rights!");
        }
        return tariffSoap;
    }
}
