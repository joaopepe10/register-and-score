package com.serasa.registerandscore.application.person.mapper;

import com.serasa.model.CreatePersonRequest;
import com.serasa.model.PagePersonResponse;
import com.serasa.registerandscore.infra.client.model.ViaCepResponse;
import com.serasa.registerandscore.infra.persistence.sql.person.model.Address;
import com.serasa.registerandscore.infra.persistence.sql.person.model.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.serasa.model.PersonResponse;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

import static java.time.LocalDate.now;
import static java.time.Period.between;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "state", source = "stateAbbreviation")
    @Mapping(target = "publicPlace", source = "street")
    Address toAddress(ViaCepResponse response);

    @Mapping(target = "age", expression = "java(getAge(entity))")
    @Mapping(target = "scoreDescription", expression = "java(getScoreDescription(entity.getScore()))")
    @Mapping(target = "address", source = "address")
    PersonResponse toResponse(PersonEntity entity);

    default Integer getAge(PersonEntity entity) {
        var difference = between(entity.getBirthDate(), now());
        return difference.getYears();
    }

    default PagePersonResponse toPageResponse(Page<PersonEntity> page) {
        var content = page.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return PagePersonResponse.builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    PersonEntity toEntity(CreatePersonRequest request, Address address);

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
