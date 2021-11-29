package com.nurtivillage.java.nutrivillageApplication.dao;

import org.springframework.data.repository.CrudRepository;

import comnurtivillage.java.nutrivillageApplication.model.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {

}
