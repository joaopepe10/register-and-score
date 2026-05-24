package com.serasa.registerandscore.controller.person;

import com.serasa.api.PersonApi;
import com.serasa.model.CreatePersonRequest;
import com.serasa.model.PagePersonResponse;
import com.serasa.model.PersonResponse;
import com.serasa.model.UpdatePersonRequest;
import com.serasa.registerandscore.application.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PersonController implements PersonApi {

    private final PersonService personService;

    @Override
    public ResponseEntity<PersonResponse> createPerson(CreatePersonRequest createPersonRequest) {
        var response = personService.createPerson(createPersonRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<PagePersonResponse> listPersons(String name, Integer minAge, Integer maxAge, String zipCode, Integer page, Integer size) {
        var response = personService.listPersons(name, minAge, maxAge, zipCode, page, size);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> updatePerson(UUID id, UpdatePersonRequest updatePersonRequest) {
        personService.updatePerson(id, updatePersonRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deletePerson(UUID id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
