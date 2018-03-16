package com.example.ec.services;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.domain.Tour;
import com.example.ec.domain.TourPackage;
import com.example.ec.repo.TourPackageRepository;
import com.example.ec.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Leandro Santos
 */
@Service
public class TourService {

    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title, String description, String blurb, Integer price, String duration, String bullets,
                           String keywords, String tourPackageName, Difficulty difficulty, Region region) {
        Optional<TourPackage> tourPackage = tourPackageRepository.findByName(tourPackageName);

        if (!tourPackage.isPresent()) {
            throw new RuntimeException("Tour package does not exist: " + tourPackageName);
        } else {
            return tourRepository.save(new Tour(title, description, blurb, price, duration, bullets,
                    keywords, tourPackage.get(), difficulty, region));
        }
    }

    public Iterable<Tour> lookup(){
        return tourRepository.findAll();
    }

    public long total(){
        return tourRepository.count();
    }
}
