package com.serasa.registerandscore.application.person;

import com.serasa.model.CreatePersonRequest;
import com.serasa.model.PagePersonResponse;
import com.serasa.model.UpdatePersonRequest;
import com.serasa.registerandscore.application.person.mapper.PersonMapper;
import com.serasa.registerandscore.core.exception.FailedUpdateInactivePersonException;
import com.serasa.registerandscore.infra.client.UserInformationProvider;
import com.serasa.registerandscore.infra.client.model.ViaCepResponse;
import com.serasa.registerandscore.infra.persistence.sql.person.PersonRepository;
import com.serasa.registerandscore.infra.persistence.sql.person.model.Address;
import com.serasa.registerandscore.infra.persistence.sql.person.model.PersonEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private UserInformationProvider userInformationProvider;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    @DisplayName("Deve criar uma pessoa com sucesso buscando endereço via CEP")
    void shouldCreatePersonSuccessfully() {
        var request = new CreatePersonRequest();
        request.setZipCode("30130010");
        request.setName("John Doe");

        var viaCep = ViaCepResponse.builder().zipCode("30130010").build();
        var address = Address.builder().zipCode("30130010").build();
        var entity = PersonEntity.builder().name("John Doe").build();

        when(userInformationProvider.findInformationByZipCode("30130010")).thenReturn(viaCep);
        when(personMapper.toAddress(viaCep)).thenReturn(address);
        when(personMapper.toEntity(request, address)).thenReturn(entity);
        when(personRepository.save(entity)).thenReturn(entity);

        personService.createPerson(request);

        verify(personRepository).save(entity);
        verify(personMapper).toResponse(entity);
    }

    @Test
    @DisplayName("Deve listar pessoas filtrando por idade exata calculando o range de datas")
    void shouldListPersonsWithAgeFilter() {
        int age = 31;
        var page = new PageImpl<>(List.of(new PersonEntity()));
        var expectedResponse = new PagePersonResponse();

        when(personRepository.findPersonsByFilters(any(), any(), any(LocalDate.class), any(LocalDate.class), any(PageRequest.class)))
                .thenReturn(page);
        when(personMapper.toPageResponse(page)).thenReturn(expectedResponse);

        var response = personService.listPersons(null, age, null, 0, 10);

        ArgumentCaptor<LocalDate> minDateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> maxDateCaptor = ArgumentCaptor.forClass(LocalDate.class);

        verify(personRepository).findPersonsByFilters(eq(null), eq(null), minDateCaptor.capture(), maxDateCaptor.capture(), any());
        
        assertThat(response).isEqualTo(expectedResponse);
        assertThat(minDateCaptor.getValue()).isBefore(maxDateCaptor.getValue());
    }

    @Test
    @DisplayName("Deve atualizar dados da pessoa quando ela existir e estiver ativa")
    void shouldUpdatePersonSuccessfully() {
        var id = UUID.randomUUID();
        var request = new UpdatePersonRequest();
        request.setName("New Name");
        var entity = PersonEntity.builder().id(id).active(true).name("Old Name").build();

        when(personRepository.findById(id)).thenReturn(Optional.of(entity));

        personService.updatePerson(id, request);

        assertThat(entity.getName()).isEqualTo("New Name");
        verify(personRepository).save(entity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar pessoa inativa")
    void shouldThrowExceptionWhenUpdatingInactivePerson() {
        var id = UUID.randomUUID();
        var entity = PersonEntity.builder().active(false).build();
        when(personRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> personService.updatePerson(id, new UpdatePersonRequest()))
                .isInstanceOf(FailedUpdateInactivePersonException.class)
                .hasMessage("Person is not active");
    }

    @Test
    @DisplayName("Deve realizar exclusão lógica da pessoa")
    void shouldDeletePersonLogically() {
        var id = UUID.randomUUID();
        var entity = PersonEntity.builder().id(id).active(true).build();
        when(personRepository.findById(id)).thenReturn(Optional.of(entity));

        personService.deletePerson(id);

        assertThat(entity.isActive()).isFalse();
        verify(personRepository).save(entity);
    }
}
