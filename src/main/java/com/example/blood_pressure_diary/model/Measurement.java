package com.example.blood_pressure_diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Measurement {

    private String date;
    private LocalTime time;
    private String rr;
    private String pulse;
    private Long idUser;
    private String description;
    private Long idIdUser;

}
