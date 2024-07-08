package com.example.personcarmanager.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCarDTO {

    @NotNull
    private Long id;

    @NotNull
    private String model;

    @NotNull
    @Min(value = 1, message = "Horsepower must be greater than 0")
    private Integer horsepower;

    @NotNull
    private Long ownerId;

}
