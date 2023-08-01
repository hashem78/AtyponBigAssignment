package com.hashem.p1;

import java.util.LinkedHashMap;
import java.util.Map;

public class Mediator<Q extends Query, C extends Command> {

    private final Map<Class<? extends Q>, QueryHandler<? extends Q, ?>> queryMap;
    private final Map<Class<? extends C>, CommandHandler<? extends C, ?>> commandMap;

    public Mediator() {
        this.queryMap = new LinkedHashMap<>();
        this.commandMap = new LinkedHashMap<>();
    }

    <Q2 extends Q, R extends Response> Mediator<Q, C> register(Class<Q2> query, QueryHandler<Q2, R> handler) {
        queryMap.putIfAbsent(query, handler);
        return this;
    }

    <C2 extends C, R extends Response> Mediator<Q, C> register(Class<C2> command, CommandHandler<C2, R> handler) {
        commandMap.putIfAbsent(command, handler);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R extends Response> R run(Q query) {
        QueryHandler<Q, R> handler = (QueryHandler<Q, R>) queryMap.get(query.getClass());
        return handler.handle(query);
    }

    @SuppressWarnings("unchecked")
    public <R extends Response> R run(C command) {
        CommandHandler<C, R> handler = (CommandHandler<C, R>) commandMap.get(command.getClass());
        return handler.handle(command);
    }
}