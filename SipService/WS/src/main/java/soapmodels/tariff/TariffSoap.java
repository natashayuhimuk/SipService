package soapmodels.tariff;

import lombok.*;
import models.Tariff;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TariffSoap {
    private ArrayList<Tariff> tariffs;
    private String message;
    private boolean success;
}
