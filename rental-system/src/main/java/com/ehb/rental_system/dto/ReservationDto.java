package com.ehb.rental_system.dto;

import com.ehb.rental_system.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {

    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private Long price;
    private ReservationStatus reservationStatus;

    private Long productId;

    private String productName;

    private String productType;

    private Long userId;

    private String username;

}
