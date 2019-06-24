package validator;

import models.Tariff;
import models.User;
import soapmodels.user.UserSoapCreatAdmin;
import soapmodels.user.UserSoapUpd;
import soapmodels.user.UserSoapUpdAdmin;

import java.util.regex.Pattern;

public class Validator {

    private static String passPattern = "^\\w{8,20}$";

    private static String fioPattern = "^[A-Za-zА-Яа-яеЁ ,.'-]{2,16}$";

    private static String tarrifName = "^\\w{2,16}$";

    private static String phoneNumber = "^375\\d{9}$";


    public static  boolean isValidTariff(Tariff tariff){
        return Pattern.matches(tarrifName, tariff.getName()) && tariff.getCost() > 0;
    }

    public static boolean isValidUserUpdate(UserSoapUpdAdmin userUpdate){
        return  Pattern.matches(fioPattern, userUpdate.getFirstName()) &&
                Pattern.matches(fioPattern, userUpdate.getSecondName()) &&
                Pattern.matches(fioPattern, userUpdate.getLastName()) &&
                userUpdate.getBalance() > 0 && Pattern.matches(tarrifName, userUpdate.getTariff()) && null != userUpdate.getNewRole();
    }

    public static boolean isValidUserUpdate(UserSoapUpd userUpdate){
        return  Pattern.matches(fioPattern, userUpdate.getFirstName()) &&
                Pattern.matches(fioPattern, userUpdate.getSecondName()) &&
                Pattern.matches(fioPattern, userUpdate.getLastName()) &&
                userUpdate.getBalance() > 0 && Pattern.matches(tarrifName, userUpdate.getTariff());
    }

    public static boolean isValidUser(UserSoapCreatAdmin user){
        boolean fio = Pattern.matches(fioPattern, user.getFirstName()) &&
                Pattern.matches(fioPattern, user.getLastName()) &&
                Pattern.matches(fioPattern, user.getSecondName());
        boolean tariff = Pattern.matches(tarrifName,user.getTariff());
        boolean balance = user.getBalance() > 0;
        return fio && tariff && balance && null != user.getPassword()
                && Pattern.matches(passPattern, null != user.getPassword() ? user.getPassword() : "")
                && null != user.getPhoneNumber()
                && Pattern.matches(phoneNumber,null != user.getPhoneNumber() ? user.getPhoneNumber() : "");
    }

    public static boolean isValidPass(String pass){
        return Pattern.matches(passPattern, pass);
    }
}
