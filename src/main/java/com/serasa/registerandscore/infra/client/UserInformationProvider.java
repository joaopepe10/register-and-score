package com.serasa.registerandscore.infra.client;

import com.serasa.registerandscore.infra.client.model.ViaCepResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInformationProvider {

    private final ViaCepClient viaCepClient;

    public ViaCepResponse findInformationByZipCode(String zipCode) {
        try {
            var response = viaCepClient.getCep(zipCode);
            if (isNull(response)) {
                throw new RuntimeException();
            }

            return response;
        } catch (FeignException e) {
            log.error("Error when trying to find information by zip code {}", zipCode, e);
        }
        return null;
    }
}
