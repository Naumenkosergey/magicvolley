package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.entity.RoleEntity;
import ru.magicvolley.enums.Role;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.RoleRepository;
import ru.magicvolley.request.AddUserRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    @Transactional
    public RoleEntity getById(UUID id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public RoleEntity getRoleForRequest(AddUserRequest addUserRequest) {

        if (addUserRequest.getIsAdmin()) {
            return roleRepository.findByRole(Role.ADMIN)
                    .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.ADMIN));
        }

        else if (addUserRequest.getIsModerator()) {
            return roleRepository.findByRole(Role.MODERATOR)
                    .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.MODERATOR));
        }

        else  {
            return roleRepository.findByRole(Role.USER)
                    .orElseThrow(() -> new EntityNotFoundException(RoleEntity.class, Role.USER));
        }
    }

}

