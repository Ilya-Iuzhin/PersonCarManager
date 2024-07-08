package com.example.personcarmanager.service.impl;

import com.example.personcarmanager.dto.request.AllCarDTO;
import com.example.personcarmanager.entity.Car;
import com.example.personcarmanager.entity.Person;
import com.example.personcarmanager.repository.CarRepository;
import com.example.personcarmanager.repository.PersonRepository;
import com.example.personcarmanager.service.CarService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    @Transactional
    @Override
    public void saveCar(AllCarDTO carDTO) {

        Optional.of(carDTO)
                .filter(dto -> carRepository.existsById(dto.getId()))
                .ifPresent(dto -> {
                    throw new ValidationException("Car with id " + dto.getId() + " already exists");
                });

        Person owner = Optional.of(carDTO)
                .map(AllCarDTO::getOwnerId)
                .flatMap(personRepository::findById)
                .orElseThrow(() -> new ValidationException("Owner with id " + carDTO.getOwnerId() + " not found"));

        Optional.of(owner)
                .map(Person::getBirthdate)
                .map(birthdate -> birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .filter(birthdate -> birthdate.plusYears(18).isBefore(LocalDate.now()))
                .orElseThrow(() -> new ValidationException("Owner must be at least 18 years old"));

        Car car = new Car();
        car.setId(carDTO.getId());
        car.setModel(carDTO.getModel());
        car.setHorsepower(carDTO.getHorsepower());
        car.setOwner(owner);
        carRepository.save(car);
    }
}



