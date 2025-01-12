package com.ehb.rental_system.entity;


import com.ehb.rental_system.dto.ProductDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private Long price;
    private Boolean available;

    public ProductDto getProductDto(){
        ProductDto dto = new ProductDto();
        dto.setId(id);
        dto.setName(name);
        dto.setType(type);
        dto.setPrice(price);
        dto.setAvailable(available);
        return dto;
    }




}
