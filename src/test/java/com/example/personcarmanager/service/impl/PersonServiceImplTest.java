package com.example.personcarmanager.service.impl;

import com.example.personcarmanager.dto.request.AllCarDTO;
import com.example.personcarmanager.dto.request.AllPersonDTO;
import com.example.personcarmanager.dto.request.PersonWithCarsDTO;
import com.example.personcarmanager.entity.Car;
import com.example.personcarmanager.entity.Person;
import com.example.personcarmanager.repository.CarRepository;
import com.example.personcarmanager.repository.PersonRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void savePersonPositiveExceptionCheck() {
        AllPersonDTO personDTO = new AllPersonDTO();
        personDTO.setId(1L);
        personDTO.setName("John Doe");
        when(personRepository.existsById(personDTO.getId())).thenReturn(false);
        personService.savePerson(personDTO);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void savePersonNegativeExceptionCheck() {
        AllPersonDTO personDTO = new AllPersonDTO();
        personDTO.setId(1L);
        personDTO.setName("John Doe");
        when(personRepository.existsById(personDTO.getId())).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            personService.savePerson(personDTO);
        });
        assertEquals("Person with id 1 already exists", exception.getMessage());
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    public void getPersonWithCarsPositiveCheck() {

        Long personId = 1L;
        Person person = new Person();
        person.setId(personId);
        person.setName("John Doe");
        person.setBirthdate(new Date());

        Car car1 = new Car();
        car1.setId(1L);
        car1.setModel("Model S");
        car1.setHorsepower(700);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setModel("Model X");
        car2.setHorsepower(600);
        List<Car> cars = Arrays.asList(car1, car2);
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(carRepository.findByOwnerId(personId)).thenReturn(cars);

        PersonWithCarsDTO result = personService.getPersonWithCars(personId);
        assertNotNull(result);
        assertEquals(personId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(person.getBirthdate(), result.getBirthdate());
        assertEquals(2, result.getCars().size());

        AllCarDTO carDTO1 = result.getCars().get(0);
        AllCarDTO carDTO2 = result.getCars().get(1);
        assertEquals(1L, carDTO1.getId());
        assertEquals("Model S", carDTO1.getModel());
        assertEquals(700, carDTO1.getHorsepower());
        assertEquals(2L, carDTO2.getId());
        assertEquals("Model X", carDTO2.getModel());
        assertEquals(600, carDTO2.getHorsepower());
    }

    @Test
    public void getPersonWithCarsNegativeCheck() {

        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            personService.getPersonWithCars(personId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Person not found", exception.getReason());
    }

    @Test
    public void clearDataPositiveTest() {

        personService.clearData();
        verify(personRepository, times(1)).deleteAllData();
    }

    @Test
    public void clearDataNegativeTest() {

        doThrow(new RuntimeException("Database error")).when(personRepository).deleteAllData();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personService.clearData();
        });

        assertEquals("Database error", exception.getMessage());
        verify(personRepository, times(1)).deleteAllData();
    }




}