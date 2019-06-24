package lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeStatistic {

    RECEIVED_CALLS("number of received calls"),
    SUCCESSFUL_NORMALIZ("number of successful normalization of numbers"),
    UNSUCCESSFUL_NORMALIZ("number of failed normalization of numbers"),
    SUCCESSFUL_REDIRECTIONS("number of calls successfully processed by the service"),
    UNSUCCESSFUL_REDIRECTIONS("number of calls unsuccessfully processed by the service");

    private String type;

}
