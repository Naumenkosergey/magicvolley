package ru.magicvolley.entity;

import ru.magicvolley.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleEntity {

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Role role;
}
