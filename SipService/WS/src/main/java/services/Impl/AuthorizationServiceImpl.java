package services.Impl;

import lib.TypeLog;
import models.Authorization;
import models.User;
import repository.AuthorizationDAO;
import repository.UserDAO;
import service.LoggerService;
import services.AuthorizationService;
import soapmodels.Message;
import soapmodels.user.UserSoap;
import token.TokenProvider;
import validator.Validator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebService(endpointInterface = "services.AuthorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {

    @EJB
    AuthorizationDAO authDAO;
    @EJB
    UserDAO userDAO;
    @EJB
    LoggerService LOGGER;

    @PostConstruct
    private void init() {
        if (LOGGER == null) {
            try {
                LOGGER = (LoggerService) new InitialContext().lookup("mappedLogService#service.LoggerService");
            } catch (NamingException e) {
                throw new RuntimeException("Failed initiation LOGGER in AuthorizationService" + e.getMessage());
            }
        }
        if (authDAO == null) {
            try {
                authDAO = (AuthorizationDAO) new InitialContext().lookup("mappedAuthorizationDAO#repository.AuthorizationDAO");
            } catch (NamingException e) {
                LOGGER.addLog(TypeLog.ERRORR, "Failed initiation authDAO in AuthorizationService", new Object[]{e});
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
    public UserSoap loginUser(String pN, String pass) {
        UserSoap userSoap = new UserSoap();
        userSoap.setMessage("Login failure! Data is not correct!");
        userSoap.setSuccess(false);
        if (null != pN && null != pass && !pN.isEmpty() && !pass.isEmpty()) {
            Authorization auth = null;
            User user = userDAO.findById(pN);
            boolean status = false;
            if (user != null) {
                auth = authDAO.findByUser(user);
            } else {
                userSoap.setMessage("Login failure! User not found!");
                return userSoap;
            }
            if (auth != null) {
                status = auth.getPassword().hashCode() == TokenProvider.createPassword(pass).hashCode();
            } else {
                userSoap.setMessage("Login failure! Password is not correct");
                return userSoap;
            }
            if (status) {
                userSoap.setSuccess(true);
                userSoap.setMessage("Login successful");
                userSoap.setToken(TokenProvider.createToken(pN));
                userSoap.setUser(user);
                return userSoap;
            }
        }
        return userSoap;
    }

    @Override
    public UserSoap reloginUser(String token) {
        UserSoap reloginUser = new UserSoap();
        reloginUser.setMessage("Login failure");
        reloginUser.setSuccess(false);
        if (null != token && !token.isEmpty() && TokenProvider.isValidToken(token)) {
            String pN = TokenProvider.getPhoneNumberFromToken(token);
            User findUser = userDAO.findById(pN);
            if (null != findUser) {
                reloginUser.setUser(findUser);
                reloginUser.setMessage("Login successful");
                reloginUser.setSuccess(true);
            }else
                reloginUser.setMessage("User not found!");
        }else
            reloginUser.setMessage("Token is not valid!");
        return reloginUser;
    }

    @Override
    public Message changePassword(String token, String curPass, String newPass) {
        Message message = new Message();
        message.setMessage("Password don't updated!");
        message.setSuccess(false);
        if (null != token && TokenProvider.isValidToken(token)) {
            if (null != curPass && null != newPass && !curPass.isEmpty() && !newPass.isEmpty()) {
                String pN = TokenProvider.getPhoneNumberFromToken(token);
                User findUser = userDAO.findById(pN);
                if (null != findUser) {
                    Authorization auth = authDAO.findByUser(findUser);
                    if (null != auth && Validator.isValidPass(newPass) && auth.getPassword().hashCode() == TokenProvider.createPassword(curPass).hashCode()) {
                        auth.setPassword(TokenProvider.createPassword(newPass));
                        authDAO.addAndUpdate(auth);
                        message.setMessage("Password id updated!");
                        message.setSuccess(true);
                    }else{
                        message.setMessage("Data is not correct! Current password or new password is not valid!");
                    }
                }else{
                    message.setMessage("User not found!");
                }
            }else{
                message.setMessage("Data is not correct! Current password or new password is not valid!");
            }
        }else{
            message.setMessage("Token is not valid");
        }
        return message;
    }
}
