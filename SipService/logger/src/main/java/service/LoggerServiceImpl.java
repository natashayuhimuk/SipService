package service;

import lib.TypeLog;
import org.apache.log4j.Logger;

import javax.ejb.*;

@Startup
@Stateful(name = "LogService", mappedName = "mappedLogService")
@EJB(name = "java:global/LogService", beanInterface = LoggerService.class)
@Remote(LoggerService.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class LoggerServiceImpl implements LoggerService {

    private static final String ST_POINTER = "weblogic.ejb.container.internal.RemoteBusinessIntfProxy.invoke(RemoteBusinessIntfProxy.java:85)";

    final static Logger log = Logger.getLogger(LoggerServiceImpl.class);

    @Override
    public void addLog(TypeLog typeLog, String message, Object[] obj) {
        switch (typeLog) {
            case INFO: {
                log.info(String.format(getClassTrace(Thread.currentThread().getStackTrace(), message), obj[0]));
            }
            break;
            case ERRORR: {
                log.error(getClassTrace(Thread.currentThread().getStackTrace(), message), (Throwable) obj[0]);
            }
            break;
            default: {
                log.error("Unknown type log: " + typeLog);
            }
        }
    }

    private String getClassTrace(StackTraceElement[] stackTraceElements, String message) {
        int index = 0;
        for (int i = 0; i < stackTraceElements.length; i++){
            if (stackTraceElements[i].toString().equals(ST_POINTER)){
                index = i + 2;
                break;
            }
        }
        return "ClassName="
                + stackTraceElements[index].getClassName()
                +"; MethodName="
                + stackTraceElements[index].getMethodName()
                +"; Message: " + message;
    }
}
