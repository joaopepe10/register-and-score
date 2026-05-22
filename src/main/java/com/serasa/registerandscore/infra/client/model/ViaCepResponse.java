package com.serasa.registerandscore.infra.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ViaCepResponse(
        @JsonProperty("cep") String zipCode,
        @JsonProperty("logradouro") String publicPlace,
        @JsonProperty("bairro") String neighborhood,
        @JsonProperty("localidade") String location,
        @JsonProperty("uf") String uf
) {
}
