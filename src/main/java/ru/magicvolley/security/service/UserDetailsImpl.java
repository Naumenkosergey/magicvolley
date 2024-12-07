package ru.magicvolley.security.service;

import ru.magicvolley.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UUID id;
    @JsonIgnore
    private String password;
    private String username;
    private String email;
    private String telephone;
    private boolean isBlocked;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserEntity user) {

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getPassword(),
                user.getUsername(),
                user.getEmail(),
                user.getTelephone(),
                user.getIsBlocked(),
                authorities);
    }
}
