package com.serasa.registerandscore.application.person.mapper;

import com.serasa.registerandscore.infra.client.model.ViaCepResponse;
import com.serasa.registerandscore.infra.persistence.sql.person.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.serasa.model.PersonResponse;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "state", source = "stateAbbreviation")
    @Mapping(target = "publicPlace", source = "street")
    Address toAddress(ViaCepResponse response);

    @Mapping(target = "age", expression = "java(java.time.Period.between(entity.getBirthDate(), java.time.LocalDate.now()).getYears())")
    @Mapping(target = "scoreDescription", expression = "java(getScoreDescription(entity.getScore()))")
    @Mapping(target = "address", source = "address")
    PersonResponse toResponse(com.serasa.registerandscore.infra.persistence.sql.person.model.PersonEntity entity);

    default String getScoreDescription(int score) {
        if (score <= 200) {
            return "Insuficiente";
        }
        if (score <= 500) {
            return "Inaceitável";
        }
        if (score <= 700) {
            return "Aceitável";
        }
        return "Recomendável";
    }
}
