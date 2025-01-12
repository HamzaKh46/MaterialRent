package com.ehb.rental_system.services.student.product;

import com.ehb.rental_system.dto.ProductDtoListDto;
import com.ehb.rental_system.dto.ProductsResponseDto;
import com.ehb.rental_system.dto.SearchProductDto;

public interface ProductService {
    ProductsResponseDto getAvailableProducts(int pageNumber);

    ProductDtoListDto searchProduct(SearchProductDto searchProductDto) ;

}
