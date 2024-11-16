package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.magicvolley.dto.PackageCardDto;
import ru.magicvolley.repository.PackageCardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackageCardService {

    private final PackageCardRepository packageCardRepository;

    public List<PackageCardDto> getDropdown() {
        return packageCardRepository.findAll().stream()
                .map(PackageCardDto::new)
                .collect(Collectors.toList());
    }
}
