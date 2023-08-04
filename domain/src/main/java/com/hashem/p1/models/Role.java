package com.hashem.p1.models;

import lombok.Builder;

@Builder
public record Role(int id, String name) implements Comparable<Role> {
    @Override
    public int compareTo(Role role) {
        return id - role.id;
    }
}
