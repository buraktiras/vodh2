package com.vodafoneH2.vodh2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkModel {
    private String plate;
    private String color;
    private String type;

}
