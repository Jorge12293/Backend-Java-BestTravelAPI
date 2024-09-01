package com.example.test.best_travel.infrastructure.services;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.best_travel.domain.repositories.mongo.AppUserRepository;
import com.example.test.best_travel.infrastructure.abstract_services.ModifyUserService;
import com.example.test.best_travel.util.exceptions.UsernameNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AppUserService implements ModifyUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public Map<String, Boolean> enabled(String username) {
        var user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException((COLLECTION_NAME)));
        user.setEnabled(!user.isEnabled());
        var userSaved = appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(), user.isEnabled());
    }

    @Override
    public Map<String, Set<String>> addRole(String username, String role) {
        var user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException((COLLECTION_NAME)));
        user.getRole().getGrantedAuthorities().add(role);
        var userSaved = appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        log.info("User {} add role {}",userSaved.getUsername(),userSaved.getRole().getGrantedAuthorities());
        return Collections.singletonMap(user.getUsername(), authorities); 
    }

    @Override
    public Map<String, Set<String>> removeRole(String username, String role) {
         var user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException((COLLECTION_NAME)));
        user.getRole().getGrantedAuthorities().remove(role);
        var userSaved = appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        log.info("User {} remove role {}",userSaved.getUsername(),userSaved.getRole().getGrantedAuthorities());
        return Collections.singletonMap(user.getUsername(), authorities);
    }

    private static final String COLLECTION_NAME = "app_user";


    @Transactional(readOnly = true)
    public void loadUserByUsername(String username) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(COLLECTION_NAME));
        System.out.println(user);
    }
}
