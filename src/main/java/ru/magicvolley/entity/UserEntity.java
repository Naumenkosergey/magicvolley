package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private UUID id;
    private String username;
    private String email;
    private String telephone;
    private String password;
    private Boolean isBlocked;
    private UUID roleId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    private RoleEntity role;

    @Version
    private Long version;
}
