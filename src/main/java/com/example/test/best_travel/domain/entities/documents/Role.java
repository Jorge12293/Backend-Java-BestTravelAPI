package com.example.test.best_travel.domain.entities.documents;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Field(name = "granted_authorities")
    private Set<String> grantedAuthorities;

}
