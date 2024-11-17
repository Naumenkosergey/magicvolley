package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.PackageCardDto;
import ru.magicvolley.entity.PackageCardEntity;
import ru.magicvolley.exceptions.EntityNotFoundException;
import ru.magicvolley.repository.PackageCardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackageCardService {

    private final PackageCardRepository packageCardRepository;

    @Transactional(readOnly = true)
    public List<PackageCardDto> getDropdown() {
        return packageCardRepository.findAll().stream()
                .map(PackageCardDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer create(PackageCardDto packageCardRequest) {
        PackageCardEntity packageCardEntity = PackageCardEntity.builder()
                .name(packageCardRequest.getName())
                .info(packageCardRequest.getInfo())
                .costNamingLink(packageCardRequest.getCostNamingLink())
                .build();
        packageCardRepository.save(packageCardEntity);

        return packageCardRequest.getPackageId();
    }

    @Transactional
    public Integer update(PackageCardDto packageCardRequest) {
        PackageCardEntity packageCardFromDb = packageCardRepository.findById(packageCardRequest.getPackageId())
                .orElseThrow(() -> new EntityNotFoundException(PackageCardEntity.class, packageCardRequest.getPackageId()));
        packageCardFromDb.setInfo(packageCardRequest.getInfo());
        packageCardFromDb.setName(packageCardRequest.getName());
        packageCardFromDb.setCostNamingLink(packageCardRequest.getCostNamingLink());
        packageCardRepository.save(packageCardFromDb);

        return packageCardRequest.getPackageId();
    }

    @Transactional
    public Boolean delete(Integer id) {
        PackageCardEntity packageCardFromDb = packageCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PackageCardEntity.class,id));
        packageCardRepository.delete(packageCardFromDb);
        return true;
    }
}
