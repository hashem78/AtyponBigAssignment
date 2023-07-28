package com.hashem.p1.models;

import com.hashem.p1.interfaces.Root;

public record User(int id, String email, String password) implements Root {

}

