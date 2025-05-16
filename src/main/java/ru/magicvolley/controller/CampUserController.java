package ru.magicvolley.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.magicvolley.dto.ConfirmReservationDto;
import ru.magicvolley.dto.ReservationDto;
import ru.magicvolley.request.AddUserCampRequest;
import ru.magicvolley.response.api.ApiResponse;
import ru.magicvolley.service.CampUserService;

@RestController
@RequestMapping("/magicvolley/camp-user")
@AllArgsConstructor
public class CampUserController {

    private final CampUserService campUserService;

    @PostMapping()
    public ApiResponse<String> makeReservation(@RequestBody ReservationDto reservationRequest) {
        return new ApiResponse<>(campUserService.makeReservation(reservationRequest));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> confirmOrUnReservation(@RequestBody ConfirmReservationDto confirmReservationRequest) {
        return new ApiResponse<>(campUserService.confirmReservation(confirmReservationRequest));

    }

    @PostMapping("/add-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Boolean> addUserToCamp(@RequestBody AddUserCampRequest addUserCampRequest){
        boolean isCreateUser = campUserService.addUserToCamp(addUserCampRequest);
        return new ApiResponse<>(isCreateUser);
    }
}
