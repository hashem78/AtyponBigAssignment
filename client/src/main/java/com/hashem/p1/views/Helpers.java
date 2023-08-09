package com.hashem.p1.views;

import com.hashem.p1.models.CClass;
import com.hashem.p1.models.Grade;
import com.hashem.p1.models.Role;
import com.hashem.p1.models.User;
import com.hashem.p1.views.core.ViewMapping;
import com.hashem.p1.views.core.ViewProperties;
import de.vandermeer.asciitable.AsciiTable;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Helpers {
    public static void displayUsers(Set<User> users) {
        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Email", "Password", "Roles");
        table.addRule();

        users.forEach(user -> {
            var roles = user.roles().stream()
                    .map(Role::name)
                    .collect(Collectors.joining(", "));

            table.addRow(user.id(), user.email(), user.passwordHash(), roles);
            table.addRule();
        });

        System.out.println(table.render());
    }

    public static void displayUser(User user) {

        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Email", "Password", "Roles");
        table.addRule();

        var roles = user.roles().stream()
                .map(Role::name)
                .collect(Collectors.joining(", "));

        table.addRow(user.id(), user.email(), user.passwordHash(), roles);

        table.addRule();
        System.out.println(table.render());
    }

    public static void displayClass(CClass clazz) {

        var table = new AsciiTable();

        System.out.println("----- " + clazz.name() + "(" + clazz.id() + ")" + " -----");
        table.addRule();
        table.addRow("Id", "Email", "Roles");
        table.addRule();
        clazz.users().forEach(user -> {
            var roles = user.roles().stream()
                    .map(Role::name)
                    .collect(Collectors.joining(", "));
            table.addRow(user.id(), user.email(), roles);
            table.addRule();
        });

        if (clazz.users().size() % 2 == 0)
            table.addRule();

        System.out.println(table.render());
    }

    public static void displayClasses(Set<CClass> classes) {
        for (var clazz : classes) {
            displayClass(clazz);
        }
    }


    public static void displayRoles(Set<Role> roles) {
        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Name");
        table.addRule();

        roles.stream().sorted().forEach(role -> {
            table.addRow(role.id(), role.name());
            table.addRule();
        });

        System.out.println(table.render());
    }

    public static void displayRole(Role role) {

        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Name");
        table.addRule();
        table.addRow(role.id(), role.name());
        table.addRule();
        System.out.println(table.render());
    }

    public static void displayGrades(List<Grade> grades) {
        var table = new AsciiTable();
        table.addRule();
        table.addRow("GradeId", "Grade");
        table.addRule();
        for (var grade : grades) {
            table.addRow(grade.id(), grade.grade());
            table.addRule();
        }
        System.out.println(table.render());
    }

    public static int getUserChoice(List<ViewProperties> properties) {
        IntStream.range(0, properties.size())
                .mapToObj(i -> new ViewMapping(i, properties.get(i)))
                .forEach(System.out::println);
        System.out.print("Enter your choice: ");
        var scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
