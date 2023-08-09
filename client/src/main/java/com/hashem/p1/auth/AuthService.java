package com.hashem.p1.auth;

import com.hashem.p1.UserDao;
import com.hashem.p1.UserDoesNotExistException;
import com.hashem.p1.models.User;

import java.sql.SQLException;

public class AuthService {

    final UserDao userDao;

    User user = null;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void logout() {
        user = null;
    }

    public void loginWithEmailAndPassword(String email, String passwordHash) throws SQLException, UserDoesNotExistException {
        user = userDao.getByEmailAndPassword(email, passwordHash);
    }

    public User getUser() {
        return user;
    }

    public AuthState getAuthState() {
        return user == null ? AuthState.LoggedOut : AuthState.LoggedIn;
    }
}
