package com.nurtivillage.java.nutrivillageApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import comnurtivillage.java.nutrivillageApplication.model.NewLocationToken;
import comnurtivillage.java.nutrivillageApplication.model.UserLocation;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(UserLocation userLocation);

}
