package ru.magicvolley.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.magicvolley.dto.MediaStorageInfo;
import ru.magicvolley.entity.HomePageEntity;
import ru.magicvolley.repository.HomePageRepository;
import ru.magicvolley.request.HomeContactBlockRequest;
import ru.magicvolley.request.HomeMainBlockRequest;
import ru.magicvolley.response.HomePageResponse;
import ru.magicvolley.response.LinkInfoResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final CoachService coachService;
    private final CampService campService;
    private final MediaService mediaService;
    private final QuestionService questionService;
    private final HomePageRepository homePageRepository;
    @Value("${media.prefix.url}")
    private String prefixUrlMedia;


    public HomePageResponse.MainBlockResponse getMainBlock(HomePageEntity homeFromDb) {
        return HomePageResponse.MainBlockResponse.builder()
                .mainImage(new MediaStorageInfo(homeFromDb.getMainImage(), prefixUrlMedia))
                .title(homeFromDb.getTitle())
                .subtitle(homeFromDb.getSubtitle())
                .build();
    }

    public HomePageResponse.ContactBlockResponse getContact(HomePageEntity homeFromDb) {
        return HomePageResponse.ContactBlockResponse.builder()
                .imageAdmin(new MediaStorageInfo(homeFromDb.getImageAdmin(), prefixUrlMedia))
                .textUnderImage(homeFromDb.getTextUnderImage())
                .contacts(homeFromDb.getContacts())
                .email(homeFromDb.getEmail())
                .linkTg(homeFromDb.getLinkTg())
                .linkVk(homeFromDb.getLinkVk())
                .linkInstagram(homeFromDb.getLinkInstagram())
                .build();
    }

    @Transactional(readOnly = true)
    public HomePageResponse getHome() {

        HomePageEntity homeFromDb = homePageRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Home page not found"));


        return HomePageResponse.builder()
                .id(homeFromDb.getId())
                .mainBlock(getMainBlock(homeFromDb))
                .contactBlock(getContact(homeFromDb))
                .camps(campService.getAll())
                .questions(questionService.getAll())
                .build();
    }

    @Transactional
    public UUID updateMainBlock(HomeMainBlockRequest request) {
        HomePageEntity homeFromDb = homePageRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Home page not found"));

        homeFromDb.setTitle(request.getTitle());
        homeFromDb.setSubtitle(request.getSubtitle());
        homeFromDb.setMainImageId(request.getMainImage().getId());
        homePageRepository.save(homeFromDb);
        return homeFromDb.getId();
    }

    @Transactional
    public UUID updateContactBlock(HomeContactBlockRequest request) {
        HomePageEntity homeFromDb = homePageRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Home page not found"));

        homeFromDb.setMainImageId(request.getImageAdmin().getId());
        homeFromDb.setTextUnderImage(request.getTextUnderImage());
        homeFromDb.setContacts(request.getContacts());
        homeFromDb.setEmail(request.getEmail());
        homeFromDb.setLinkTg(request.getLinkTg());
        homeFromDb.setLinkVk(request.getLinkVk());
        homeFromDb.setLinkInstagram(request.getLinkInstagram());
        homePageRepository.save(homeFromDb);
        return homeFromDb.getId();
    }

    @Transactional(readOnly = true)
    public LinkInfoResponse getAppLinks() {
        HomePageEntity homeFromDb = homePageRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Home page not found"));
        return LinkInfoResponse.builder()
                .linkTg(homeFromDb.getLinkTg())
                .linkVk(homeFromDb.getLinkVk())
                .linkInstagram(homeFromDb.getLinkInstagram())
                .build();
    }
}
