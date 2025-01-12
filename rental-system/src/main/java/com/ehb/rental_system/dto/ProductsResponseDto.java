package com.ehb.rental_system.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProductsResponseDto {
    private List<ProductDto> productDtoList;

    private int totalPages;
    private int pageNumber;
}
