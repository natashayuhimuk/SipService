package services;

import models.Tariff;
import soapmodels.tariff.TariffSoap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService
public interface TariffService {

    @WebResult(name = "response")
    @WebMethod
    TariffSoap getTariffs();

    @WebResult(name = "response")
    @WebMethod
    TariffSoap addTariff(@WebParam(name = "token")String token, @WebParam(name = "tariff") Tariff tariff);

    @WebResult(name = "response")
    @WebMethod
    TariffSoap editTariff(@WebParam(name = "token")String token, @WebParam(name = "tariff") Tariff tariff);

    @WebResult(name = "response")
    @WebMethod
    TariffSoap deleteTariffs(@WebParam(name = "token") String token, @WebParam(name = "tariffs")ArrayList<String> tariffs);
}
