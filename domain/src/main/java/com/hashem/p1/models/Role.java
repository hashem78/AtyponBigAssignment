package com.hashem.p1.models;

import com.hashem.p1.interfaces.Root;

public record Role(int id, int user_id, String name) implements Root {
}
