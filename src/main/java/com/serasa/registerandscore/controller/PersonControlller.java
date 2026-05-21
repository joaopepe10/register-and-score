package com.serasa.registerandscore.controller;

import com.serasa.registerandscore.application.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/person")
@RequiredArgsConstructor
public class PersonControlller {

    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<PersonResponse> register(@RequestBody CreatePersonRequest request) {
        personService.register(request);
        return ResponseEntity.ok(new PersonResponse());
    }
}
