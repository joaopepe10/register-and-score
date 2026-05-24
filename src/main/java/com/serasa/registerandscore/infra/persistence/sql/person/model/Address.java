package com.serasa.registerandscore.infra.persistence.sql.person.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String zipCode;
    private String state;
    private String city;
    private String neighborhood;
    private String publicPlace;

}
