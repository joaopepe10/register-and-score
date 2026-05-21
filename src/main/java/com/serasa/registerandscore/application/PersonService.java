package com.serasa.registerandscore.application;

import com.serasa.registerandscore.controller.CreatePersonRequest;
import com.serasa.registerandscore.infra.client.AddressClient;
import com.serasa.registerandscore.infra.persistence.sql.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final AddressClient addressClient;
    private final PersonRepository personRepository;

    public void register(CreatePersonRequest request) {
        addressClient.findInformationByZipCode(request.getZipCode());
    }
}
