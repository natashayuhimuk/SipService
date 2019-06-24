package soapmodels.user;

import lombok.*;
import models.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSoap {
    private String token;
    private User user;
    private String message;
    private boolean success;

}
