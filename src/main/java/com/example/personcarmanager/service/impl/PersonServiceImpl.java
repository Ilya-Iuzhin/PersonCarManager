package com.example.personcarmanager.service.impl;

import com.example.personcarmanager.dto.request.AllCarDTO;
import com.example.personcarmanager.dto.request.AllPersonDTO;
import com.example.personcarmanager.dto.request.PersonWithCarsDTO;
import com.example.personcarmanager.entity.Car;
import com.example.personcarmanager.entity.Person;
import com.example.personcarmanager.repository.CarRepository;
import com.example.personcarmanager.repository.PersonRepository;
import com.example.personcarmanager.service.PersonService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final CarRepository carRepository;

    @Transactional
    @Override
    public void savePerson(AllPersonDTO personDTO) {

        Stream.of(personDTO)
                .filter(dto -> personRepository.existsById(dto.getId()))
                .findFirst()
                .ifPresent(dto -> {
                    throw new ValidationException("Person with id " + dto.getId() + " already exists");
                });

        Person person = new Person();
        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
        personRepository.save(person);
    }

    @Override
    public PersonWithCarsDTO getPersonWithCars(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        Person person = personOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

        List<Car> cars = carRepository.findByOwnerId(personId);

        PersonWithCarsDTO personWithCarsDTO = new PersonWithCarsDTO();
        personWithCarsDTO.setId(person.getId());
        personWithCarsDTO.setName(person.getName());
        personWithCarsDTO.setBirthdate(person.getBirthdate());

        List<AllCarDTO> carDTOList = new ArrayList<>();
        for (Car car : cars) {
            AllCarDTO carDTO = new AllCarDTO();
            carDTO.setId(car.getId());
            carDTO.setModel(car.getModel());
            carDTO.setHorsepower(car.getHorsepower());
            carDTOList.add(carDTO);
        }
        personWithCarsDTO.setCars(carDTOList);

        return personWithCarsDTO;
    }

    public void clearData() {
        personRepository.deleteAllData();
    }


}