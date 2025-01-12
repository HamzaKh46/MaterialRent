package com.ehb.rental_system.controller.student;


import com.ehb.rental_system.dto.SearchProductDto;
import com.ehb.rental_system.services.student.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class ProductController {
    private final ProductService productService;


    @GetMapping("/products/{pageNumber}")
    public ResponseEntity<?> getAvailableProducts(@PathVariable int pageNumber) {
        return ResponseEntity.ok(productService.getAvailableProducts(pageNumber));

    }

    @PostMapping("/product/search")
    public ResponseEntity<?> searchProduct(@RequestBody SearchProductDto searchProductDto) {
        return ResponseEntity.ok(productService.searchProduct(searchProductDto));

    }
}
