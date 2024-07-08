package com.example.personcarmanager.service.impl;

import com.example.personcarmanager.dto.request.StatisticsDTO;
import com.example.personcarmanager.repository.PersonRepository;
import com.example.personcarmanager.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final PersonRepository personRepository;

    @Override
    public StatisticsDTO getStatistics() {

        StatisticsDTO result = personRepository.getStatistics();
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setPersonCount(result.getPersonCount());
        statisticsDTO.setCarCount(result.getCarCount());
        statisticsDTO.setUniqueVendorCount(result.getUniqueVendorCount());
        return statisticsDTO;
    }
}
