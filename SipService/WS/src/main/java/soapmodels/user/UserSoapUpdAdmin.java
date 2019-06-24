package soapmodels.user;

import lib.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSoapUpdAdmin {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String secondName;
    private double balance;
    private String tariff;
    private Roles newRole;
}
