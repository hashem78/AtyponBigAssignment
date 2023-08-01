package com.hashem.p1.auth;

import com.hashem.p1.models.User;

import java.sql.DriverManager;
import java.sql.SQLException;

public class AuthService {

    User user = null;

    public void logout() {
        user = null;
    }

    public void loginWithEmailAndPassword(String email, String password) throws AuthException {

        try (var connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/dbo", "root", "Mythi@2024")) {

            var query = "select * from Users where email = ? and password = ?";
            var statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            var result = statement.executeQuery();

            if (result.next()) {
                user = new User(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("password")
                );
            } else {
                throw new AuthException("User not found");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public AuthState getAuthState() {
        return user == null ? AuthState.LoggedOut : AuthState.LoggedIn;
    }
}
