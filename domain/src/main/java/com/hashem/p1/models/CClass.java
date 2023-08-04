package com.hashem.p1.models;

import com.hashem.p1.interfaces.Root;
import lombok.Builder;
import lombok.With;

import java.util.Set;

@With
@Builder
public record CClass(int id, String name, Set<User> users) implements Root {

}
