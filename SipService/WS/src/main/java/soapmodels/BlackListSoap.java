package soapmodels;
import lombok.*;
import soapmodels.user.UserNumbers;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlackListSoap {
    private UserNumbers blacklist;
    private String message;
    private boolean success;
}
