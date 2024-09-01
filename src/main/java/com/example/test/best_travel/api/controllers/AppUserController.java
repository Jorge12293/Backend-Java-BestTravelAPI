package com.example.test.best_travel.api.controllers;

import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.best_travel.infrastructure.abstract_services.ModifyUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "user")
@AllArgsConstructor
@Tag(name = "User")
public class AppUserController {
    
    private final ModifyUserService modifyUserService;

    @Operation(summary = "Enabled or disabled user")
    @PatchMapping(path = "enabled-or-disabled")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Map<String,Boolean>> enabledOrDisabled(@RequestParam String username){
        return ResponseEntity.ok(modifyUserService.enabled(username));
    }

    @Operation(summary = "Add role user")
    @PatchMapping(path = "add-role")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Map<String,Set<String>>> addRole(@RequestParam String username,@RequestParam String role){
        return ResponseEntity.ok(modifyUserService.addRole(username, role));
    }

    @Operation(summary = "Remove role user")
    @PatchMapping(path = "remove-role")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Map<String,Set<String>>> removeRole(@RequestParam String username,@RequestParam String role){
        return ResponseEntity.ok(modifyUserService.removeRole(username, role));
    }


}
