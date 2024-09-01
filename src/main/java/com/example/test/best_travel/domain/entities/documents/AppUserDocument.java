package com.example.test.best_travel.domain.entities.documents;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collation = "app_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserDocument implements Serializable{
    
    @Id
    private String id;
    private String dni;
    private String username;
    private boolean enabled;
    private String password;
    private Role role;
}
