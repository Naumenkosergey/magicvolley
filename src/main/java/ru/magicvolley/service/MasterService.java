package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.MasterEntity;
import ru.magicvolley.entity.MediaStorageEntity;
import ru.magicvolley.enums.TypeEntity;
import ru.magicvolley.repository.MasterRepository;
import ru.magicvolley.request.AboutUsRequest;
import ru.magicvolley.response.AboutUsResponse;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MasterService {

    private final MasterRepository masterRepository;
    private final MediaService mediaService;
    @Value("${media.prefix.url}")
    private String prefixUrlMedia;

    public AboutUsResponse.Master getMaster() {
        MasterEntity masterEntity = masterRepository.findAll().stream().findFirst().orElse(null);
        if (Objects.nonNull(masterEntity)) {
            return AboutUsResponse.Master.builder()
                    .id(masterEntity.getId())
                    .name(masterEntity.getName_master())
                    .image(new MediaStorageInfo(masterEntity.getAvatarMaster(), prefixUrlMedia))
                    .infos(Arrays.stream(masterEntity.getInfo().split(";")).toList())
                    .build();
        }
        return null;
    }

    public void setMaster(AboutUsRequest.Master masterRequest) {
        MasterEntity masterFromDb = masterRepository.findAll().stream().findFirst().orElse(null);

        MediaStorageEntity avatarMaster = mediaService.mediaInfoToMediaStorage(masterRequest.getImage(), masterFromDb.getId(), TypeEntity.MASTER);
        masterFromDb.setName_master(masterRequest.getName());
        masterFromDb.setAvatarMaster(avatarMaster);
        masterFromDb.setAvatarMasterId(avatarMaster.getId());
        masterFromDb.setInfo(String.join(";", masterRequest.getInfos()));
    }


}
