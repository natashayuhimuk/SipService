package services;

import models.Configuration;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ConfigurationService {

    @WebResult(name = "configuration")
    @WebMethod
    List<Configuration> getAllConfigs();

}
