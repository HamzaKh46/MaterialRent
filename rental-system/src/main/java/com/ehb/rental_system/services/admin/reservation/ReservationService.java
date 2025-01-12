package com.ehb.rental_system.services.admin.reservation;

import com.ehb.rental_system.dto.ReservationResponseDto;

public interface ReservationService {
    ReservationResponseDto getAllReservations(int pageNumber);
    boolean changeReservationStatus(Long id, String status);
}
