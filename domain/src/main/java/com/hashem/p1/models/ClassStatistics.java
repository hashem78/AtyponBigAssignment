package com.hashem.p1.models;

public record ClassStatistics(
        float average,
        float max,
        float min,
        float deviation,
        float variance,
        float range,
        float median,
        float mode
) {

    public ClassStatistics() {
        this(-1,-1,-1,-1,-1,-1,-1,-1);
    }

    static ClassStatistics empty() {
        return new ClassStatistics(-1, -1, -1, -1, -1, -1, -1, -1);
    }
}
