package com.ehb.rental_system.services.student.product;

import com.ehb.rental_system.dto.ProductDtoListDto;
import com.ehb.rental_system.dto.ProductsResponseDto;
import com.ehb.rental_system.dto.SearchProductDto;
import com.ehb.rental_system.entity.Product;
import com.ehb.rental_system.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductsResponseDto getAvailableProducts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);

        Page<Product> productPage = productRepository.findByAvailable(true,pageable);

        ProductsResponseDto productsResponseDto = new ProductsResponseDto();
        productsResponseDto.setPageNumber(productPage.getPageable().getPageNumber());
        productsResponseDto.setTotalPages(productPage.getTotalPages());
        productsResponseDto.setProductDtoList(productPage.stream().map(Product::getProductDto).collect(Collectors.toList()));

        return productsResponseDto;
    }

    @Override
    public ProductDtoListDto searchProduct(SearchProductDto searchProductDto) {
        Product product = new Product();
        product.setName(searchProductDto.getName());
        product.setPrice(searchProductDto.getPrice());
        product.setType(searchProductDto.getType());
        ExampleMatcher exampleMatcher =ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("price", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Product> productExample = Example.of(product, exampleMatcher);
        List<Product> productList = productRepository.findAll(productExample);
        ProductDtoListDto productDtoListDto = new ProductDtoListDto();
        productDtoListDto.setProductDtoList(productList.stream().map(Product::getProductDto).collect(Collectors.toList()));

        return productDtoListDto;
    }
}
