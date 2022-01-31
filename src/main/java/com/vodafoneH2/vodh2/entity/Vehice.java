package com.vodafoneH2.vodh2.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Vehice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vehiceId;
    private String park;
    private String plate;
    private String color;
    private String type;
    private String slot;
}
