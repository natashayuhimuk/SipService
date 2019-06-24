package services.Impl;


import lib.Roles;
import models.Authorization;
import models.BlackList;
import models.Tariff;
import models.User;
import repository.AuthorizationDAO;
import repository.BlackListDAO;
import repository.TariffDAO;
import repository.UserDAO;
import services.UserService;
import soapmodels.user.*;
import token.TokenProvider;
import validator.Validator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;

@WebService(endpointInterface = "services.UserService")
public class UserServiceImpl implements UserService {

    @EJB
    UserDAO userDAO;
    @EJB
    TariffDAO tariffDAO;
    @EJB
    BlackListDAO bLDAO;
    @EJB
    AuthorizationDAO authDAO;

    @PostConstruct
    private void init() {
        if (userDAO == null) {
            try {
                userDAO = (UserDAO) new InitialContext().lookup("mappedUserDAO#repository.UserDAO");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        if (tariffDAO == null) {
            try {
                tariffDAO = (TariffDAO) new InitialContext().lookup("mappedTariffDAO#repository.TariffDAO");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        if (authDAO == null) {
            try {
                authDAO = (AuthorizationDAO) new InitialContext().lookup("mappedAuthorizationDAO#repository.AuthorizationDAO");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        if (bLDAO == null) {
            try {
                bLDAO = (BlackListDAO) new InitialContext().lookup("mappedBlackListDAO#repository.BlackListDAO");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public UserFullList getAllUsers(String token) {
        UserFullList resList = new UserFullList();
        resList.setMessage("Failure get all users! Token is not valid!");
        resList.setSuccess(false);
        if (null != token && !token.isEmpty() && TokenProvider.isValidToken(token)){
            String pN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(pN);
            if (null != admin && admin.getRole() == Roles.ADMIN){
                UserList userList = new UserList();
                userList.setUser(userDAO.findAll());
                resList.setUsers(userList);
                resList.setMessage("Success get users!");
                resList.setSuccess(true);
            }
            resList.setMessage("Failure get all users! Access insufficient access rights!");
        }
        return resList;
    }

    @Override
    public UserFullList createUser(String token, UserSoapCreatAdmin newUser) {
        UserFullList resUserList = new UserFullList();
        resUserList.setMessage("Filed create user! Token is not valid or data for create new user!");
        resUserList.setSuccess(false);
        if (null != token && null != newUser && !token.isEmpty() &&
                Validator.isValidUser(newUser) && TokenProvider.isValidToken(token)){
            String adminPN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(adminPN);
            if (null != admin && admin.getRole() == Roles.ADMIN){
                Tariff tariff = tariffDAO.findById(newUser.getTariff());
                User saveUser = userDAO.findById(newUser.getPhoneNumber());
                if (null != tariff && null == saveUser){
                    saveUser = new User();
                    saveUser.setPhoneNumber(newUser.getPhoneNumber());
                    saveUser.setFirstName(newUser.getFirstName());
                    saveUser.setLastName(newUser.getLastName());
                    saveUser.setSecondName(newUser.getSecondName());
                    saveUser.setRole(Roles.USER);
                    saveUser.setTariff(tariff);
                    saveUser.setBalance(Double.parseDouble(String.format("%.2f", newUser.getBalance()).replace(",",".")));
                    userDAO.addAndUpdate(saveUser);
                    BlackList blUser = new BlackList(saveUser);
                    Authorization authUser = new Authorization(saveUser, TokenProvider.createPassword(newUser.getPassword()));
                    bLDAO.addAndUpdate(blUser);
                    authDAO.addAndUpdate(authUser);
                    UserList userList = new UserList();
                    userList.setUser(userDAO.findAll());
                    resUserList.setUsers(userList);
                    resUserList.setMessage("Success create user!");
                    resUserList.setSuccess(true);
                }
                resUserList.setMessage("Filed create user! Tariff not found or user exists!");
            }
            resUserList.setMessage("Filed create user! Access insufficient access rights!");
        }
        return resUserList;
    }

    @Override
    public UserFullList updateUserByAdmin(String token, UserSoapUpdAdmin userAdUpd) {
        UserFullList resUserList = new UserFullList();
        resUserList.setMessage("Filed update user! Token is not valid or data for update user is not correct!");
        resUserList.setSuccess(false);
        if (null != token && null != userAdUpd && !token.isEmpty() && Validator.isValidUserUpdate(userAdUpd) && TokenProvider.isValidToken(token)){
            String adminPN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(adminPN);
            if (null != admin && admin.getRole() == Roles.ADMIN){
                User userUpd = userDAO.findById(userAdUpd.getPhoneNumber());
                if (null != userUpd){
                    Tariff newTariff = tariffDAO.findById(userAdUpd.getTariff());
                    userUpd.setFirstName(userAdUpd.getFirstName());
                    userUpd.setLastName(userAdUpd.getLastName());
                    userUpd.setSecondName(userAdUpd.getSecondName());
                    userUpd.setTariff(newTariff);
                    userUpd.setBalance(Double.parseDouble(String.format("%.2f", userAdUpd.getBalance()).replace(",",".")));
                    if (userUpd.getRole() != Roles.ADMIN)
                        userUpd.setRole(userAdUpd.getNewRole());
                    userDAO.addAndUpdate(userUpd);
                    UserList userList = new UserList();
                    userList.setUser(userDAO.findAll());
                    resUserList.setUsers(userList);
                    resUserList.setMessage("Success update user!");
                    resUserList.setSuccess(true);
                }
                resUserList.setMessage("Filed update user! User not found!");
            }
            resUserList.setMessage("Filed update user! Access insufficient access rights!");
        }
        return resUserList;
    }

    @Override
    public UserFullList deleteUsers(String token, ArrayList<String> pNs) {
        UserFullList resultUserList = new UserFullList();
        resultUserList.setMessage("Filed delete users! Token is not valid!");
        resultUserList.setSuccess(false);
        if (null != token && null != pNs && !token.isEmpty() && pNs.size() > 0 && TokenProvider.isValidToken(token)){
            String adminPN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(adminPN);
            if (null != admin && admin.getRole() == Roles.ADMIN){
                int sizeDelete = 0;
                for (String userPN : pNs ) {
                    User delCandidat = userDAO.findById(userPN);
                    if (null != delCandidat && delCandidat.getRole() != Roles.ADMIN) {
                        sizeDelete++;
                        userDAO.delete(delCandidat);
                    }
                    else if ( delCandidat.getRole() == Roles.ADMIN && pNs.size() < 2){
                        resultUserList.setSuccess(false);
                        resultUserList.setMessage("Failed delete user!! ADMINISTRATOR cannot be deleted!");
                        return resultUserList;
                    }
                }
                if (sizeDelete == 0){
                    resultUserList.setSuccess(false);
                    resultUserList.setMessage("Failed delete user!! ADMINISTRATOR's cannot be deleted!");
                    return resultUserList;
                }
                UserList userList = new UserList();
                userList.setUser(userDAO.findAll());
                resultUserList.setUsers(userList);
                resultUserList.setMessage("Success delete " + sizeDelete + " users!");
                resultUserList.setSuccess(true);
            }else
                resultUserList.setMessage("Filed delete users! Access insufficient access rights!");
        }
        return resultUserList;
    }

    @Override
    public UserSoap updateUser(String token, UserSoapUpd userUpdate) {
        UserSoap userSoap = new UserSoap();
        userSoap.setSuccess(false);
        userSoap.setMessage("User dont updated! Token is not valid or data for update not correct!");
        if (null != token && null != userUpdate && !token.isEmpty() && Validator.isValidUserUpdate(userUpdate) &&
                TokenProvider.isValidToken(token)) {
            Tariff changeTariff = tariffDAO.findById(userUpdate.getTariff());
            if (null != changeTariff) {
                String pN = TokenProvider.getPhoneNumberFromToken(token);
                User userForUpd = userDAO.findById(pN);
                if (null != userForUpd) {
                    userForUpd.setFirstName(userUpdate.getFirstName());
                    userForUpd.setLastName(userUpdate.getLastName());
                    userForUpd.setSecondName(userUpdate.getSecondName());
                    userForUpd.setBalance(Double.parseDouble(String.format("%.2f", userUpdate.getBalance()).replace(",",".")));
                    userForUpd.setTariff(changeTariff);
                    userDAO.addAndUpdate(userForUpd);
                    userSoap.setMessage("User updated!");
                    userSoap.setSuccess(true);
                    userSoap.setUser(userForUpd);
                } else
                    userSoap.setMessage("User dont updated! User not fond");
            }else
                userSoap.setMessage("User dont updated! Tariff not fond");
        }
        return userSoap;
    }

    @Override
    public Roles[] getRoles(String token) {
        if (null != token && !token.isEmpty() && TokenProvider.isValidToken(token)){
            String pN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(pN);
            if (null != admin && admin.getRole() == Roles.ADMIN)
                return Roles.values();
        }
        return null;
    }

    @Override
    public double getUserBalance(String token) {
        if (null != token && !token.isEmpty() && TokenProvider.isValidToken(token)){
            String pN = TokenProvider.getPhoneNumberFromToken(token);
            User findUser = userDAO.findById(pN);
            if (null != findUser)
                return Double.parseDouble(String.format("%.2f",findUser.getBalance()).replace(",","."));
        }
        return 0;
    }
}
