package ru.magicvolley.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {

	private UUID id;
	private String username;
	private String email;
	@JsonIgnore
	private String cookie;
	private List<String> roles;

}