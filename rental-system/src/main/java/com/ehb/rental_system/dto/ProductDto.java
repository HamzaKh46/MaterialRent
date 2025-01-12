package com.ehb.rental_system.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String type;
    private Long price;
    private Boolean available;
}
