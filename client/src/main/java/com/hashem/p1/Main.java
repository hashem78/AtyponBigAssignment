package com.hashem.p1;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

interface View {
    void run(CallbackStore store) throws CallbackNotFoundException;
}

class CallbackNotFoundException extends Exception {

    public CallbackNotFoundException(String callbackName) {
        super("Callback " + callbackName + " not found");
    }
}

class CallbackStore {
    final Map<String, View> map;

    CallbackStore(Map<String, View> map) {
        this.map = map;
    }

    private CallbackStore add(String name, View callback) {
        map.put(name, callback);
        return this;
    }

    public void call(String name) throws CallbackNotFoundException {

        if (name == null) throw new IllegalArgumentException();
        if (!map.containsKey(name)) throw new CallbackNotFoundException(name);

        map.get(name).run(this);
    }

    public static class Factory {
        final Map<String, View> map;

        Factory() {
            map = new LinkedHashMap<>();
        }

        Factory add(String name, View callback) {
            map.put(name, callback);
            return this;
        }

        CallbackStore create() {
            return new CallbackStore(map);
        }
    }
}

enum AuthState {
    LoggedIn,
    LoggedOut
}

class MainMenuView implements View {

    record ViewProperties(String name, String description) {
    }

    record ViewMapping(int index, ViewProperties properties) {
        @Override
        public String toString() {
            return index + ". " + properties.description + '\n';
        }
    }

    @Override
    public void run(CallbackStore store) throws CallbackNotFoundException {

        final var viewProperties = new ViewProperties[]{
                new ViewProperties("login", "Login to an existing account.")
        };

        IntStream.range(0, viewProperties.length)
                .mapToObj(i -> new ViewMapping(i, viewProperties[i]))
                .forEach(System.out::println);

        System.out.print("Enter your choice: ");
        var scanner = new Scanner(System.in);
        var userChoice = scanner.nextInt();
        store.call(viewProperties[userChoice].name);
    }
}

class LoginView implements View {

    @Override
    public void run(CallbackStore store) throws CallbackNotFoundException {

    }
}

class ExitView implements View {

    @Override
    public void run(CallbackStore store) {
        System.exit(0);
    }
}

public class Main {
    public static void main(String[] args) throws CallbackNotFoundException {

        var callbackStore = new CallbackStore.Factory()
                .add("main_menu", new MainMenuView())
                .add("login", new LoginView())
                .add("exit", new ExitView())
                .create();

        while (true) {
            callbackStore.call("main_menu");
        }
    }
}