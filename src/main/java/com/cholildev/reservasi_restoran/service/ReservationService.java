package com.cholildev.reservasi_restoran.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cholildev.reservasi_restoran.dto.MessageResponseDTO;
import com.cholildev.reservasi_restoran.dto.ReservationDTO;
import com.cholildev.reservasi_restoran.dto.ReservationRequestDTO;
import com.cholildev.reservasi_restoran.dto.ResponseBodyDTO;
import com.cholildev.reservasi_restoran.model.Reservation;
import com.cholildev.reservasi_restoran.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationService {

    private final ReservationRepository reservationRepository;

    // Hari libur (Rabu dan Jum'at)
    private static final List<Integer> HOLIDAYS = Arrays.asList(Calendar.WEDNESDAY, Calendar.FRIDAY);

    public MessageResponseDTO createReservation(ReservationRequestDTO request) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(request.getReservationDate());

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if (HOLIDAYS.contains(dayOfWeek)) {
            String message = "Restoran tutup pada hari Rabu dan Jum'at. Pilih hari lain.";
            return new MessageResponseDTO(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), message);
        }

        List<Reservation> reservations = reservationRepository.findAllByReservationDate(request.getReservationDate());

        if (reservations.size() >= 2) {
            String message = "Kuota reservasi penuh untuk tanggal " + request.getReservationDate();
            return new MessageResponseDTO(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), message);
        }

        Reservation reservation = new Reservation();
        reservation.setCustomerName(request.getCustomerName());
        reservation.setReservationDate(request.getReservationDate());
        reservation.setCustomerPhone(request.getCustomerPhone());
        reservation.setPersonNumber(request.getPersonNumber());

        reservationRepository.save(reservation);

        return new MessageResponseDTO(HttpStatus.OK.name(), HttpStatus.OK.value(), "Reservasi berhasil dibuat");
    }

    public ResponseBodyDTO getReservationlist() {

        List<Reservation> reservationList = reservationRepository.findAll();

        List<ReservationDTO> data = reservationList.stream().map(reservation -> {
            ReservationDTO dto = new ReservationDTO();
            dto.setReservationId(reservation.getId());
            dto.setCustomerName(reservation.getCustomerName());
            dto.setReservationDate(reservation.getReservationDate());
            dto.setCustomerPhone(reservation.getCustomerPhone());
            dto.setPersonNumber(reservation.getPersonNumber());
            return dto;
        }).collect(Collectors.toList());

        return new ResponseBodyDTO(HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                "Berhasil memuat data reservasi restoran", data);
    }
}
