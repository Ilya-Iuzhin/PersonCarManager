package com.example.personcarmanager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDTO {

    private Long personCount;
    private Long carCount;
    private Long uniqueVendorCount;
}
