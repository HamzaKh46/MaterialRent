package com.ehb.rental_system.services.admin.products;

import com.ehb.rental_system.dto.ProductDto;
import com.ehb.rental_system.dto.ProductDtoListDto;
import com.ehb.rental_system.dto.ProductsResponseDto;
import com.ehb.rental_system.dto.SearchProductDto;
import com.ehb.rental_system.entity.Product;
import com.ehb.rental_system.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {


    private final ProductRepository productRepository;

    public boolean addProduct(ProductDto productDto) {
        try {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setType(productDto.getType());
            product.setAvailable(true);
            productRepository.save(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ProductsResponseDto getAllProducts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);

        Page<Product> productPage = productRepository.findAll(pageable);

        ProductsResponseDto productsResponseDto = new ProductsResponseDto();
        productsResponseDto.setPageNumber(productPage.getPageable().getPageNumber());
        productsResponseDto.setTotalPages(productPage.getTotalPages());
        productsResponseDto.setProductDtoList(productPage.stream().map(Product::getProductDto).collect(Collectors.toList()));

        return productsResponseDto;
    }

    public ProductDto getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getProductDto();
        }else {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }

    public boolean updateProduct(Long id, ProductDto productDto){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setName(productDto.getName());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setType(productDto.getType());
            productRepository.save(existingProduct);
            return true;
        }
        return false;
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }

    @Override
    public ProductDtoListDto searchProduct(SearchProductDto searchProductDto) {
        Product product = new Product();
        product.setName(searchProductDto.getName());
        product.setPrice(searchProductDto.getPrice());
        product.setType(searchProductDto.getType());


        // ExampleMatcher defines how the fields of the search criteria (product) will be matched in the database.
        // It allows for flexible matching strategies such as case-insensitive, partial matching for the fields 'name', 'price', and 'type'.
        ExampleMatcher exampleMatcher =ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("price", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Product> productExample = Example.of(product, exampleMatcher);
        List<Product> productList = productRepository.findAll(productExample);


        // Map the list of Product entities to ProductDto objects and wrap them in a ProductDtoListDto.
        ProductDtoListDto productDtoListDto = new ProductDtoListDto();
        productDtoListDto.setProductDtoList(productList.stream().map(Product::getProductDto).collect(Collectors.toList()));

        return productDtoListDto;
    }
}
