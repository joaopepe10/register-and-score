package com.serasa.registerandscore.infra.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ViaCepResponse(
        @JsonProperty("cep") String zipCode,
        @JsonProperty("logradouro") String street,
        @JsonProperty("complemento") String complement,
        @JsonProperty("unidade") String unit,
        @JsonProperty("bairro") String neighborhood,
        @JsonProperty("localidade") String city,
        @JsonProperty("uf") String stateAbbreviation,
        @JsonProperty("estado") String state,
        @JsonProperty("regiao") String region,
        @JsonProperty("ibge") String ibge,
        @JsonProperty("gia") String gia,
        @JsonProperty("ddd") String ddd,
        @JsonProperty("siafi") String siafi
) {
}
