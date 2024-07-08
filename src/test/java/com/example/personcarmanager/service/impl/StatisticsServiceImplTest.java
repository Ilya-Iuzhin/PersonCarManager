package com.example.personcarmanager.service.impl;

import com.example.personcarmanager.dto.request.StatisticsDTO;
import com.example.personcarmanager.repository.CarRepository;
import com.example.personcarmanager.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Test
    public void getStatistics_ShouldReturnValidStatisticsDTO() {

        StatisticsDTO result = new StatisticsDTO();
        result.setPersonCount(10L);
        result.setCarCount(20L);
        result.setUniqueVendorCount(5L);
        when(personRepository.getStatistics()).thenReturn(result);

        StatisticsDTO statistics = statisticsService.getStatistics();
        assertNotNull(statistics);
        assertEquals(10, statistics.getPersonCount());
        assertEquals(20, statistics.getCarCount());
        assertEquals(5, statistics.getUniqueVendorCount());
    }

    @Test
    public void getStatistics_ShouldThrowException_WhenRepositoryReturnsNull() {

        when(personRepository.getStatistics()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            statisticsService.getStatistics();
        });
    }


}