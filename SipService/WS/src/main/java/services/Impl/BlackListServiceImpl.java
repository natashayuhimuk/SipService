package services.Impl;

import lib.Roles;
import models.BlackList;
import models.User;
import repository.BlackListDAO;
import repository.UserDAO;
import services.BlackListService;
import soapmodels.BlackListSoap;
import soapmodels.user.UserNumbers;
import token.TokenProvider;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.*;

@WebService(endpointInterface = "services.BlackListService")
public class BlackListServiceImpl implements BlackListService {

    @EJB
    BlackListDAO blDAO;
    @EJB
    UserDAO userDAO;

    @PostConstruct
    private void init(){
        if (blDAO == null) {
            try {
                blDAO = (BlackListDAO) new InitialContext().lookup("mappedBlackListDAO#repository.BlackListDAO");
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
    public BlackListSoap getBlackList(String token) {
        BlackListSoap blResList = new BlackListSoap();
        blResList.setMessage("Filed get blacklist! Token is not valid! ");
        blResList.setSuccess(false);
        if (null != token && !token.isEmpty() && TokenProvider.isValidToken(token)){
            String pN = TokenProvider.getPhoneNumberFromToken(token);
            User findUser = userDAO.findById(pN);
            if (null != findUser){
                BlackList blUser = blDAO.findByUser(findUser);
                if (null != blUser) {
                    blResList.setBlacklist(getPhoneNumbers(blUser.getUsers()));
                    blResList.setMessage("Success get blacklist!");
                    blResList.setSuccess(true);
                }else
                    blResList.setMessage("Filed get blacklist! BlackList not found!");
            }else
                blResList.setMessage("Filed get blacklist! User not found!");
        }
        return blResList;
    }

    @Override
    public BlackListSoap getBlackListByAdmin(String token, String pN) {
        BlackListSoap blResList = new BlackListSoap();
        blResList.setMessage("Filed get blacklist! Token is not valid!");
        blResList.setSuccess(false);
        if (null != token && null != pN && !token.isEmpty() && !pN.isEmpty() && TokenProvider.isValidToken(token)){
            String adminPN = TokenProvider.getPhoneNumberFromToken(token);
            User admin = userDAO.findById(adminPN);
            if (null != admin && admin.getRole() == Roles.ADMIN){
                User findUser = userDAO.findById(pN);
                if (null != findUser){
                    BlackList blUser = blDAO.findByUser(findUser);
                    blResList.setBlacklist(getPhoneNumbers(blUser.getUsers()));
                    blResList.setMessage("Success get blacklist user!");
                    blResList.setSuccess(true);
                }else
                    blResList.setMessage("Filed get blacklist! User not found!");
            }else
                blResList.setMessage("Filed get blacklist! Access insufficient access rights!");
        }
        return blResList;
    }

    @Override
    public BlackListSoap addToBlackList(String token, String pN) {
        BlackListSoap blResList = new BlackListSoap();
        blResList.setMessage("Filed add to blacklist! Token is not valid!");
        blResList.setSuccess(false);
        if (null != token && null != pN && !token.isEmpty() && !pN.isEmpty() && TokenProvider.isValidToken(token)){
            String userPN = TokenProvider.getPhoneNumberFromToken(token);
            User user = userDAO.findById(userPN);
            User user1ToBL = userDAO.findById(pN);
            if (null != user && null != user1ToBL){
                BlackList userBL = blDAO.findByUser(user);
                userBL.getUsers().add(user1ToBL);
                blDAO.addAndUpdate(userBL);
                blResList.setBlacklist(getPhoneNumbers(userBL.getUsers()));
                blResList.setMessage("Success add to blacklist");
                blResList.setSuccess(true);
            }else
                blResList.setMessage("Filed add to blacklist! User or users not found!");
        }
        return blResList;
    }

    @Override
    public BlackListSoap deleteFromBlackList(String token, ArrayList<String> pNs) {
        BlackListSoap blResList = new BlackListSoap();
        blResList.setMessage("Filed add to blacklist! Token is not valid!");
        blResList.setSuccess(false);
        if (null != token && null != pNs && !token.isEmpty() && pNs.size() > 0 && TokenProvider.isValidToken(token)){
            String userPN = TokenProvider.getPhoneNumberFromToken(token);
            User user = userDAO.findById(userPN);
            if (null != user){
                BlackList userBL = blDAO.findByUser(user);
                for (String delPN : pNs) {
                    User delCand = userDAO.findById(delPN);
                    if (null != delCand){
                        Iterator<User> it = userBL.getUsers().iterator();
                        while (it.hasNext()){
                            if (it.next().getPhoneNumber().equals(delCand.getPhoneNumber())){
                                it.remove();
                                break;
                            }
                        }
                    }
                }
                blDAO.addAndUpdate(userBL);
                blResList.setBlacklist(getPhoneNumbers(userBL.getUsers()));
                blResList.setMessage("Success del phoneNumbers in blacklist!");
                blResList.setSuccess(true);
            }else
                blResList.setMessage("Filed add to blacklist! User not found!");
        }
        return blResList;
    }

    private UserNumbers getPhoneNumbers(Set<User> users){
        UserNumbers listPN = new UserNumbers(new HashSet<String>());
        for (User u : users) {
            listPN.getNumber().add(u.getPhoneNumber());
        }
        return listPN;
    }

}
