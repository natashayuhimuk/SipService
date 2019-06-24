package filters;

import lib.TypeConfiguration;
import lib.TypeLog;
import models.Configuration;
import repository.ConfigurationDAO;
import service.LoggerService;

import javax.ejb.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless(name = "PhoneChecker")
@EJB(name = "java:global/PhoneChecker", beanInterface = PhoneChecker.class)
@Remote(PhoneChecker.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class PhoneCheckerImpl implements PhoneChecker {

    private final int PHONE_LEN = 7;

    @EJB
    private ConfigurationDAO confDAO;
    @EJB
    private LoggerService LOGGER;

    @Override
    public boolean isValidNumber(String phoneNumber) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'PhoneChecker' isValidNumber << ",new Object[]{null});
        Pattern pattern;
        Matcher matcher;
        LOGGER.addLog(TypeLog.INFO, "'PhoneChecker' isValidNumber phoneNumber = %s ",new Object[]{phoneNumber});
        for (Configuration conf : confDAO.findAll()) {
            if (conf.getName().contains(TypeConfiguration.REG_EXP.getType())) {
                pattern = Pattern.compile(conf.getDescription());
                matcher = pattern.matcher(phoneNumber);
                if (matcher.matches()) {
                    LOGGER.addLog(TypeLog.INFO, "'PhoneChecker' isValidNumber status = %s >> ",new Object[]{true});
                    return true;
                }
            } else
                continue;
        }
        LOGGER.addLog(TypeLog.INFO, "'PhoneChecker' isValidNumber status = %s >> ",new Object[]{false});
        return false;
    }

    @Override
    public String normalizeNumber(String phoneNumber) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'PhoneChecker' normalizeNumber << ",new Object[]{null});
        LOGGER.addLog(TypeLog.INFO, "Entering 'PhoneChecker' normalizeNumber phoneNumber = %s ",new Object[]{phoneNumber});
        if (phoneNumber.length() == PHONE_LEN){
            phoneNumber = "+37529" + phoneNumber;
        }else if (!phoneNumber.contains("+375")){
            phoneNumber = "+37529" + phoneNumber.substring(2, phoneNumber.length());
        }
        LOGGER.addLog(TypeLog.INFO, "Entering 'PhoneChecker' normalizeNumber phoneNumber = %s >> ",new Object[]{phoneNumber});
        return phoneNumber;
    }
}
