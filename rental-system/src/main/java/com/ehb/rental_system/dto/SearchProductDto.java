package com.ehb.rental_system.dto;

import lombok.Data;

@Data
public class SearchProductDto {
    private String name;
    private String type;
    private Long price;
}
