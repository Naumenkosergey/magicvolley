package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.ReservationDto;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.AuthService;
import ru.magicvolley.service.CampUserService;

import java.util.UUID;

@RestController
@RequestMapping("/magicvolley/camp-user")
@AllArgsConstructor
public class CampUserController {

    private final CampUserService campUserService;
    private final AuthService authService;

    @PostMapping("/{campId}")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Boolean> makeReservation(@PathVariable UUID campId) {
        UUID currentUserId = authService.getCurrentUserId();
        ReservationDto reservationDto = new ReservationDto(campId, currentUserId);
        return new ApiResponse<>(campUserService.makeReservation(reservationDto));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> confirmReservation(@RequestBody ReservationDto reservationDto) {
        return new ApiResponse<>(campUserService.confirmReservation(reservationDto));

    }
}
