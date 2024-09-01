package com.example.test.best_travel.infrastructure.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.test.best_travel.domain.entities.documents.AppUserDocument;
import com.example.test.best_travel.domain.repositories.mongo.AppUserRepository;
import com.example.test.best_travel.infrastructure.abstract_services.ModifyUserService;
import com.example.test.best_travel.util.exceptions.UsernameNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AppUserService implements ModifyUserService,UserDetailsService {

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
    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUserDocument user = this.appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(COLLECTION_NAME));
        return mapUserToUserDetails(user);
    }

    private static UserDetails mapUserToUserDetails(AppUserDocument userDocument) {
       Set<GrantedAuthority> authorities = userDocument.getRole()
               .getGrantedAuthorities()
               .stream()
               .map(SimpleGrantedAuthority::new)
               .collect(Collectors.toSet());
        System.out.println("Authority from db" + authorities);
        return new User(
                userDocument.getUsername(),
                userDocument.getPassword(),
                userDocument.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }
}
