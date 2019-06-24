package lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeConfiguration {

    TIMER("timer"),
    REG_EXP("normalization pattern");

    private String type;

}
