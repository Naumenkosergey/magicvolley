package ru.magicvolley.entity;

import lombok.*;
import ru.magicvolley.enums.Role;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleEntity {

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Role role;
}
