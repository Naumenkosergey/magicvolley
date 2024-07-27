package ru.magicvolley.request;

import lombok.Data;

@Data
public class RefreshJwtRequest {

    private String refreshToken;

}
