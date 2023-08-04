package com.hashem.p1.views.core;

public record ViewMapping(int index, ViewProperties properties) {
    @Override
    public String toString() {
        return index + ". " + properties.description();
    }
}
