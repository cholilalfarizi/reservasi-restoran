package com.cholildev.reservasi_restoran.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequestDTO {
    private String customerName;
    private String customerPhone;
    private int personNumber;
    private Date reservationDate;
}
