package com.ehb.rental_system.services.student.booking;

import com.ehb.rental_system.dto.ReservationDto;
import com.ehb.rental_system.dto.ReservationResponseDto;
import com.ehb.rental_system.entity.Product;
import com.ehb.rental_system.entity.Reservation;
import com.ehb.rental_system.entity.User;
import com.ehb.rental_system.enums.ReservationStatus;
import com.ehb.rental_system.repository.ProductRepository;
import com.ehb.rental_system.repository.ReservationRepository;
import com.ehb.rental_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public boolean postReservation(ReservationDto reservationDto) {
        Optional<User> optionalUser = userRepository.findById(reservationDto.getUserId());
        Optional<Product> optionalProduct = productRepository.findById(reservationDto.getProductId());

        if (optionalUser.isPresent() && optionalProduct.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setUser(optionalUser.get());
            reservation.setProduct(optionalProduct.get());
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            // Calculate the final price
            Long days = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckOutDate());
            reservation.setPrice(optionalProduct.get().getPrice() * days);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public ReservationResponseDto getAllReservationByUserId(Long userId,int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto).collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());

        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }
}
