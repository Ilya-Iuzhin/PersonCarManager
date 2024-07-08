package com.example.personcarmanager.controller;

import com.example.personcarmanager.dto.request.AllCarDTO;
import com.example.personcarmanager.service.CarService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    /**
     * Метод для сохранения информации о машине.
     *
     * @param carDTO DTO объект, представляющий информацию о машине, который будет сохранен
     * @return ResponseEntity с кодом 200 (OK), если сохранение прошло успешно, иначе ResponseEntity с кодом 400 (Bad Request)
     * и сообщением об ошибке в случае валидационной ошибки
     */
    @PostMapping("/addCar")
    public ResponseEntity<?> saveCar(@RequestBody AllCarDTO carDTO) {
        try {
            carService.saveCar(carDTO);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
