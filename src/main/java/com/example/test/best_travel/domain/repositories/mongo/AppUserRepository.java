package com.example.test.best_travel.domain.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.test.best_travel.domain.entities.documents.AppUserDocument;


public interface AppUserRepository extends MongoRepository<AppUserDocument,String>{

    
} 
