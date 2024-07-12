package org.example.dao;

import org.example.model.Users;

public interface LoginDAO {
    public Users Login(String email, String password);
}
