package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.ReservationDto;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CampUserService;

import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/camp-user")
@AllArgsConstructor
public class CampUserController {

    private final CampUserService campUserService;

    @PostMapping("/{campId}/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Boolean> makeReservation(@PathVariable UUID campId, @PathVariable UUID userId) {
        ReservationDto reservationDto = new ReservationDto(campId, userId);
        return new ApiResponse<>(campUserService.makeReservation(reservationDto));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> confirmReservation(ReservationDto reservationDto) {
        return new ApiResponse<>(campUserService.confirmReservation(reservationDto));

    }
}
