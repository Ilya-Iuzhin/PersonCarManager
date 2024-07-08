package com.example.personcarmanager.controller;

import com.example.personcarmanager.dto.request.StatisticsDTO;
import com.example.personcarmanager.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * Метод для получения статистики.
     *
     * @return объект ResponseEntity с объектом StatisticsDTO в теле ответа и кодом успешного выполнения запроса (200 OK)
     */
    @GetMapping("/get")
    public ResponseEntity<StatisticsDTO> getStatistics() {
        StatisticsDTO statisticsDTO = statisticsService.getStatistics();
        return ResponseEntity.ok(statisticsDTO);
    }
}
