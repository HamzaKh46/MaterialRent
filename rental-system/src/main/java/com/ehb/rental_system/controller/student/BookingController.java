package com.ehb.rental_system.controller.student;

import com.ehb.rental_system.dto.ReservationDto;
import com.ehb.rental_system.services.student.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<?> postBooking(@RequestBody ReservationDto reservationDto) {
        boolean success = bookingService.postReservation(reservationDto);
        if (success) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/bookings/{userId}/{pageNumber}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable Long userId, @PathVariable int pageNumber) {
        try {
            return ResponseEntity.ok(bookingService.getAllReservationByUserId(userId, pageNumber));

        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }
}
