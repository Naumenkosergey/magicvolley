package com.example.demomv.service;

import com.example.demomv.entity.RoleEntity;
import com.example.demomv.enums.Role;
import com.example.demomv.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    public RoleEntity getById(UUID id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Set<RoleEntity> getRoles(Set<String> strRoles) {

        if (CollectionUtils.isEmpty(strRoles)) {
            return Set.of(roleRepository.findByRole(Role.GUEST)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        }
        return strRoles.stream()
                .map(role -> switch (role) {
                    case "admin" -> roleRepository.findByRole(Role.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    case "mod" -> roleRepository.findByRole(Role.MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    default -> roleRepository.findByRole(Role.USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                })
                .collect(Collectors.toSet());
    }
}

