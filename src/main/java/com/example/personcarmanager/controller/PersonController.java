package com.example.personcarmanager.controller;

import com.example.personcarmanager.dto.request.AllPersonDTO;
import com.example.personcarmanager.dto.request.PersonWithCarsDTO;
import com.example.personcarmanager.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    /**
     * Метод для сохранения информации о человеке.
     *
     * @param personDTO DTO объект, представляющий информацию о человеке, который будет сохранен
     */
    @PostMapping("/addPerson")
    public void savePerson(@RequestBody AllPersonDTO personDTO) {
        personService.savePerson(personDTO);

    }

    /**
     * Метод для получения информации о человеке с их автомобилями.
     *
     * @param personId идентификатор человека, информацию о которой нужно получить
     * @return объект ResponseEntity с информацией о человеке с их автомобилями, если запрос успешен,
     * или объект ResponseEntity с ошибкой 404 Not Found, если человек не найден,
     * или объект ResponseEntity с ошибкой 400 Bad Request и причиной ошибки, если запрос некорректен
     */
    @GetMapping("/personWithCars")
    public ResponseEntity<?> getPersonWithCars(@RequestParam Long personId) {
        try {

            PersonWithCarsDTO personWithCarsDTO = personService.getPersonWithCars(personId);
            return ResponseEntity.ok(personWithCarsDTO);
        } catch (ResponseStatusException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().body(e.getReason());
            }
        }
    }

    /**
     * Метод для очистки данных.
     *
     * @return объект ResponseEntity с кодом успешного выполнения запроса (200 OK)
     */

    @GetMapping("/clear")
    public ResponseEntity<?> clearData() {
        personService.clearData();
        return ResponseEntity.ok().build();
    }


}
