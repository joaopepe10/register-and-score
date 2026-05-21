package com.serasa.registerandscore.infra.persistence.sql.model;


import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String zipCode;
    private String state;
    private String city;
    private String neighborhood;
    private String publicPlace;

}
