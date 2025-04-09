package org.example.controllers;

import org.example.essence.Ad;
import org.example.essence.repository.AdRepository;
import org.example.service.AdService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/ad")
public class AdController {
    private final AdRepository adRepository;
    private  final AdService advertisementService;

    public AdController(AdRepository adRepository, AdService advertisementService) {
        this.adRepository = adRepository;
        this.advertisementService = advertisementService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ad>> getAllAds() {
        List<Ad> ads = adRepository.findAll();
        if (ads.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ads);
    }
    @PostMapping("/createAd")
    public void createAdvertisements() {
        advertisementService.createAdvertisements();
    }
}
