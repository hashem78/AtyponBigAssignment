package com.hashem.p1.models;

import com.hashem.p1.interfaces.Root;

public record Grade(int id, int user_id, String email, int class_id, float grade) implements Root {

}
