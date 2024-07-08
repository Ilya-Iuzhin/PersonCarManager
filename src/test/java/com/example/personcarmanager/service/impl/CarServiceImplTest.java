package com.example.personcarmanager.service.impl;

import com.example.personcarmanager.dto.request.AllCarDTO;
import com.example.personcarmanager.entity.Person;
import com.example.personcarmanager.repository.CarRepository;
import com.example.personcarmanager.repository.PersonRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void saveCar_ShouldSaveCar_WhenDataIsValid() {

        AllCarDTO carDTO = new AllCarDTO();
        carDTO.setId(1L);
        carDTO.setModel("Model S");
        carDTO.setHorsepower(700);
        carDTO.setOwnerId(1L);
        Person owner = new Person();
        owner.setId(1L);
        owner.setBirthdate(Date.from(LocalDate.of(1980, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(personRepository.findById(carDTO.getOwnerId())).thenReturn(Optional.of(owner));
        when(carRepository.existsById(carDTO.getId())).thenReturn(false);
        carService.saveCar(carDTO);
        verify(carRepository, times(1)).save(any());
    }

    @Test
    public void saveCar_ShouldThrowValidationException_WhenOwnerNotFound() {

        AllCarDTO carDTO = new AllCarDTO();
        carDTO.setId(1L);
        carDTO.setModel("Model S");
        carDTO.setHorsepower(700);
        carDTO.setOwnerId(1L);
        when(personRepository.findById(carDTO.getOwnerId())).thenReturn(Optional.empty());
        assertThrows(ValidationException.class, () -> {
            carService.saveCar(carDTO);
        });
    }

    @Test
    public void saveCar_ShouldThrowValidationException_WhenOwnerIsUnder18() {

        AllCarDTO carDTO = new AllCarDTO();
        carDTO.setId(1L);
        carDTO.setModel("Model S");
        carDTO.setHorsepower(700);
        carDTO.setOwnerId(1L);

        Person owner = new Person();
        owner.setId(1L);
        owner.setBirthdate(Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(personRepository.findById(carDTO.getOwnerId())).thenReturn(Optional.of(owner));
        assertThrows(ValidationException.class, () -> {
            carService.saveCar(carDTO);
        });
    }




}