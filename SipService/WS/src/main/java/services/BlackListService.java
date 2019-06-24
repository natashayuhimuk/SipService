package services;

import soapmodels.BlackListSoap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService
public interface BlackListService {

    @WebResult(name = "response")
    @WebMethod
    BlackListSoap getBlackList(@WebParam(name = "token") String token);

    @WebResult(name = "response")
    @WebMethod
    BlackListSoap getBlackListByAdmin(@WebParam(name = "token") String token, @WebParam(name = "phoneNumber") String pN);

    @WebResult(name = "response")
    @WebMethod
    BlackListSoap addToBlackList(@WebParam(name = "token") String token, @WebParam(name = "phoneNumber") String pN);

    @WebResult(name = "response")
    @WebMethod
    BlackListSoap deleteFromBlackList(@WebParam(name = "token") String token, @WebParam(name = "phoneNumbers") ArrayList<String> pNs);

}
