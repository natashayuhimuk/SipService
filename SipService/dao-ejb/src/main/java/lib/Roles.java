package lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {

    USER("user"),
    ADMIN("administrator");

    private String role;

}
