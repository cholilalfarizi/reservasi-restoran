package com.cholildev.reservasi_restoran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cholildev.reservasi_restoran.dto.MessageResponseDTO;
import com.cholildev.reservasi_restoran.dto.ReservationRequestDTO;
import com.cholildev.reservasi_restoran.dto.ResponseBodyDTO;
import com.cholildev.reservasi_restoran.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createReservation(@RequestBody ReservationRequestDTO request) {
        MessageResponseDTO response = reservationService.createReservation(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping
    public ResponseEntity<ResponseBodyDTO> getSales() {
        ResponseBodyDTO responseBodyDTO = reservationService.getReservationlist();
        return ResponseEntity.ok(responseBodyDTO);
    }
}
