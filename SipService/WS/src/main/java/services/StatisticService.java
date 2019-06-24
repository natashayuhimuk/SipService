package services;

import soapmodels.statistics.StatisticsSoapList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface StatisticService {

    @WebResult(name = "response")
    @WebMethod
    StatisticsSoapList getStatistics(@WebParam(name = "token") String token);

}
