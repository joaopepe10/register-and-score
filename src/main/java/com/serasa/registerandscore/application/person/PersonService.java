package com.serasa.registerandscore.application.person;

import com.serasa.model.CreatePersonRequest;
import com.serasa.model.PagePersonResponse;
import com.serasa.model.PersonResponse;
import com.serasa.model.UpdatePersonRequest;
import com.serasa.registerandscore.application.person.mapper.PersonMapper;
import com.serasa.registerandscore.infra.client.UserInformationProvider;
import com.serasa.registerandscore.infra.persistence.sql.person.PersonRepository;
import com.serasa.registerandscore.infra.persistence.sql.person.model.Address;
import com.serasa.registerandscore.infra.persistence.sql.person.model.PersonEntity;
import com.serasa.registerandscore.infra.persistence.sql.person.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final UserInformationProvider userInformationProvider;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonResponse createPerson(CreatePersonRequest request) {
        var address = getAddress(request);

        var personEntity = PersonEntity.builder()
                .name(request.getName())
                .address(address)
                .birthDate(request.getBirthDate())
                .phoneNumber(request.getPhoneNumber())
                .score(request.getScore())
                .role(UserRole.USER)
                .build();

        personEntity = personRepository.save(personEntity);
        return personMapper.toResponse(personEntity);
    }

    private Address getAddress(CreatePersonRequest request) {
        var address = userInformationProvider.findInformationByZipCode(request.getZipCode());
        return personMapper.toAddress(address);
    }

    public PagePersonResponse listPersons(String name, Integer minAge, Integer maxAge, String zipCode, Integer page, Integer size) {
        LocalDate minDate = null;
        LocalDate maxDate = null;

        if (nonNull(minAge)) {
            maxDate = LocalDate.now().minusYears(minAge);
        }
        if (nonNull(maxAge)) {
            minDate = LocalDate.now().minusYears(maxAge + 1).plusDays(1);
        }

        var searchName = isBlank(name) ? null : name;
        var searchZipCode = isBlank(zipCode) ? null : zipCode;

        var result = personRepository.findPersonsByFilters(searchName, searchZipCode, minDate, maxDate, PageRequest.of(page, size));
        var content = result.getContent().stream().map(personMapper::toResponse).collect(Collectors.toList());
        return PagePersonResponse.builder()
                .content(content)
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements((int) result.getTotalElements())
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements((int) result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    public void updatePerson(UUID id, UpdatePersonRequest request) {
        var personEntity = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        if (personEntity.isNotActive()) {
            throw new RuntimeException("Person not found");
        }

        if (nonNull(request.getName())) {
            personEntity.setName(request.getName());
        }
        if (nonNull(request.getPhoneNumber())) {
            personEntity.setPhoneNumber(request.getPhoneNumber());
        }
        if (nonNull(request.getScore())) {
            personEntity.setScore(request.getScore());
        }

        personRepository.save(personEntity);
    }

    public void deletePerson(UUID id) {
        var personEntity = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        personEntity.setActive(false);
        personRepository.save(personEntity);
    }
}
