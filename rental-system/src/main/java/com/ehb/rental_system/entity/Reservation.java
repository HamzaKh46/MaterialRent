package com.ehb.rental_system.entity;


import com.ehb.rental_system.dto.ReservationDto;
import com.ehb.rental_system.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private Long price;
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id",nullable = false) // Defines the foreign key column for the product in the reservation table.
    @OnDelete(action = OnDeleteAction.CASCADE) // If the associated product is deleted, the reservation will be automatically deleted.
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // If the associated user is deleted, the reservation will be automatically deleted.
    private User user;

    public ReservationDto getReservationDto() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(id);
        reservationDto.setPrice(price);
        reservationDto.setCheckInDate(checkInDate);
        reservationDto.setCheckOutDate(checkOutDate);
        reservationDto.setReservationStatus(reservationStatus);

        reservationDto.setUserId(user.getId());
        reservationDto.setUsername(user.getName());
        reservationDto.setProductId(product.getId());
        reservationDto.setProductName(product.getName());
        reservationDto.setProductType(product.getType());

        return reservationDto;
    }

}
