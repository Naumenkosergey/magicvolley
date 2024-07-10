package com.example.demomv.entity;

import com.example.demomv.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity {

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Role role;
}
