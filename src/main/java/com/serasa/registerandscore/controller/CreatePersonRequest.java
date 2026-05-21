package com.serasa.registerandscore.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreatePersonRequest {

    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private String zipCode;
}
