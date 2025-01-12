package com.ehb.rental_system.controller.admin;



import com.ehb.rental_system.dto.SearchProductDto;
import com.ehb.rental_system.entity.Product;
import com.ehb.rental_system.services.admin.products.ProductsService;

import com.ehb.rental_system.dto.ProductDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ProductsController {


    private final ProductsService productsService;


    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
        boolean success = productsService.addProduct(productDto);
        if (success) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/products/{pageNumber}")
    public ResponseEntity<?> getAllProducts(@PathVariable int pageNumber) {
        return ResponseEntity.ok(productsService.getAllProducts(pageNumber));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productsService.getProductById(id));
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        boolean success = productsService.updateProduct(id, productDto);
        if (success) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }


    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productsService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product/search")
    public ResponseEntity<?> searchProduct(@RequestBody SearchProductDto searchProductDto) {
        return ResponseEntity.ok(productsService.searchProduct(searchProductDto));

    }
}
