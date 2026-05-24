package com.serasa.registerandscore.core.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ErrorResponse(
        HttpStatus status,
        ErrorCode code,
        String error,
        String detail
) {
}
