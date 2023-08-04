package com.hashem.p1.views.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class ViewRegistry {
    final Map<String, View> map;

    ViewRegistry(Map<String, View> map) {
        this.map = map;
    }

    private ViewRegistry add(String name, View callback) {
        map.put(name, callback);
        return this;
    }

    public View get(String name) {

        return map.get(name);
    }

    public static class Factory {
        final Map<String, View> map;

        public Factory() {
            map = new LinkedHashMap<>();
        }

        public ViewRegistry.Factory register(String name, View callback) {
            map.put(name, callback);
            return this;
        }

        public ViewRegistry create() {
            return new ViewRegistry(map);
        }
    }
}
