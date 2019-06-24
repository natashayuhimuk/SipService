package service;

import lib.TypeLog;

import javax.ejb.Remote;

@Remote
public interface LoggerService {

    void addLog(TypeLog typeLog, String message, Object[] obj);

}
