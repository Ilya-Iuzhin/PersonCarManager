package com.example.personcarmanager.service;

import com.example.personcarmanager.dto.request.AllPersonDTO;
import com.example.personcarmanager.dto.request.PersonWithCarsDTO;

public interface PersonService {
    void savePerson(AllPersonDTO personDTO);


    PersonWithCarsDTO getPersonWithCars(Long personId);

    void clearData();
}
