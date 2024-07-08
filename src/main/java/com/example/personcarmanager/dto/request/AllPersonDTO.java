package com.example.personcarmanager.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPersonDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @Past
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthdate;
    private List<AllCarDTO> cars;

}
