package com.hashem.p1.models;

import com.hashem.p1.interfaces.Root;
import lombok.Builder;
import lombok.With;

import java.util.Set;

@Builder
@With
public record User(
        int id,
        String email,
        String passwordHash,
        Set<Role> roles) implements Root {

    public boolean hasRole(String name) {
        var role = roles.stream()
                .filter(r -> r.name().equals(name))
                .findFirst();

        return role.isPresent();
    }
}

