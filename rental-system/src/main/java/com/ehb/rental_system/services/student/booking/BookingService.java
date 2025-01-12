package com.ehb.rental_system.services.student.booking;

import com.ehb.rental_system.dto.ReservationDto;
import com.ehb.rental_system.dto.ReservationResponseDto;

public interface BookingService {
    boolean postReservation(ReservationDto reservationDto);
    ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber);
}
