package com.example.blood_pressure_diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Measurement {

    private Long id;
    private LocalDate date;
    private String rr;
    private String pulse;
    private Long idUser;

}
