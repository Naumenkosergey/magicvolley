package ru.magicvolley.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {

	private UUID id;
	private String username;
	private String telephone;
	private String email;
	private String cookie;
	private String role;

}