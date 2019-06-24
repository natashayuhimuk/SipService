package filters;

import javax.ejb.Remote;

@Remote
public interface PhoneChecker {

    boolean isValidNumber(String phoneNumber);

    String normalizeNumber(String phoneNumber);

}
