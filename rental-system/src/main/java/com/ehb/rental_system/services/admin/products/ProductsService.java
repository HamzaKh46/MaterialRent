package com.ehb.rental_system.services.admin.products;

import com.ehb.rental_system.dto.ProductDto;
import com.ehb.rental_system.dto.ProductDtoListDto;
import com.ehb.rental_system.dto.ProductsResponseDto;
import com.ehb.rental_system.dto.SearchProductDto;

public interface ProductsService {
    boolean addProduct(ProductDto productDto);
    ProductsResponseDto getAllProducts(int pageNumber);
    ProductDto getProductById(Long id);
    boolean updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);

    ProductDtoListDto searchProduct(SearchProductDto searchProductDto) ;
}
