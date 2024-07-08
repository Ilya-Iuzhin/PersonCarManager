package com.example.personcarmanager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonWithCarsDTO {

    private Long id;
    private String name;
    private Date birthdate;
    private List<AllCarDTO> cars;
}
