package soapmodels.statistics;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticsSoapList {
    private String updateTime;
    private ArrayList<StatSoap> statistics;
    private String message;
    private boolean success;
}
