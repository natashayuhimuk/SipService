package services;

import soapmodels.Message;
import soapmodels.user.UserSoap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface AuthorizationService {

    @WebResult(name = "response")
    @WebMethod
    UserSoap loginUser(@WebParam(name = "phoneNumber") String pN, @WebParam(name = "password") String pass);

    @WebResult(name = "response")
    @WebMethod
    UserSoap reloginUser(@WebParam(name = "token") String token);

    @WebResult(name = "response")
    @WebMethod
    Message changePassword(@WebParam(name = "token") String token,
                           @WebParam(name = "currentPassword") String curPass,
                           @WebParam(name = "newPassword") String newPass);

}
