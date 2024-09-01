package com.example.test.best_travel.domain.repositories.mongo;



import com.example.test.best_travel.domain.entities.documents.AppUserDocument;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface AppUserRepository extends MongoRepository<AppUserDocument,String>{

    Optional<AppUserDocument> findByUsername(String username);

} 
