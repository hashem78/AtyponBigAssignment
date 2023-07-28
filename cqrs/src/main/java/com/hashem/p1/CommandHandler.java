package com.hashem.p1;

public interface CommandHandler<C extends Command, R> {
    R handle(C command);
}