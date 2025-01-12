package com.ehb.rental_system.services.admin.reservation;

import com.ehb.rental_system.dto.ReservationDto;
import com.ehb.rental_system.dto.ReservationResponseDto;
import com.ehb.rental_system.entity.Product;
import com.ehb.rental_system.entity.Reservation;
import com.ehb.rental_system.enums.ReservationStatus;
import com.ehb.rental_system.repository.ProductRepository;
import com.ehb.rental_system.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    private final ProductRepository productRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public ReservationResponseDto getAllReservations(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto).collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());

        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }



    // Method to change the status of a reservation based on its ID and the provided status ("Approve" or "Reject").
    // It updates both the reservation status and the availability of the associated product.
    public boolean changeReservationStatus(Long id, String status) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        // If the reservation exists, proceed with status update
        if (optionalReservation.isPresent()) {
            Reservation existingReservation = optionalReservation.get();


            // Set the reservation status based on the provided status ("Approve" or "Reject")
            if(Objects.equals(status, "Approve")) {
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);

            }else{
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
            }
            reservationRepository.save(existingReservation);

            // Update the product availability to 'false' (product is no longer available for booking)
            Product existingProduct = existingReservation.getProduct();
            existingProduct.setAvailable(false);
            productRepository.save(existingProduct);
            return true;


        }
        return false;

    }
}
