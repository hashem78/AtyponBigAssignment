package com.hashem.p1;

public interface QueryHandler<Q extends Query, R> {
    R handle(Q query);
}
