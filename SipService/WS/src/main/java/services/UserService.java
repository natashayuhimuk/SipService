package services;

import lib.Roles;
import models.User;
import soapmodels.user.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService
public interface UserService {

    @WebResult(name = "response")
    @WebMethod
    UserFullList getAllUsers(@WebParam(name = "token") String token);

    @WebResult(name = "response")
    @WebMethod
    UserFullList createUser(@WebParam(name = "token") String token,@WebParam(name = "user") UserSoapCreatAdmin newUser);

    @WebResult(name = "response")
    @WebMethod
    UserFullList updateUserByAdmin(@WebParam(name = "token") String token, @WebParam(name = "user") UserSoapUpdAdmin userAdUpd);

    @WebResult(name = "response")
    @WebMethod
    UserFullList deleteUsers(@WebParam(name = "token") String token, @WebParam(name = "users") ArrayList<String> pNs);

    @WebResult(name = "response")
    @WebMethod
    UserSoap updateUser(@WebParam(name = "token") String token, @WebParam(name = "user") UserSoapUpd userUpdate);

    @WebResult(name = "response")
    @WebMethod
    Roles[] getRoles(@WebParam(name = "token") String token);

    @WebResult(name = "response")
    @WebMethod
    double getUserBalance(@WebParam(name = "token") String token);

}
