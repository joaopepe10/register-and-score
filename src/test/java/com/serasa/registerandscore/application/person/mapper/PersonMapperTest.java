package com.serasa.registerandscore.application.person.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @ParameterizedTest
    @CsvSource({
            "0, Insuficiente",
            "200, Insuficiente",
            "201, Inaceitável",
            "500, Inaceitável",
            "501, Aceitável",
            "700, Aceitável",
            "701, Recomendável",
            "1000, Recomendável"
    })
    @DisplayName("Deve retornar a descrição correta baseada no score")
    void shouldReturnCorrectScoreDescription(int score, String expectedDescription) {
        String description = mapper.getScoreDescription(score);
        assertThat(description).isEqualTo(expectedDescription);
    }
}
