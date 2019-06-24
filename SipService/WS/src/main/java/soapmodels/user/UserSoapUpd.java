package soapmodels.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSoapUpd {
    private String firstName;
    private String lastName;
    private String secondName;
    private double balance;
    private String tariff;
}
