package com.example.servlet.repo;

import com.example.servlet.db.Database;
import com.example.servlet.model.Role;
import com.example.servlet.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public void migrate() throws Exception {
        try (Connection c = Database.getConnection();
             Statement s = c.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS users (id BIGSERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL UNIQUE, password_hash VARCHAR(255) NOT NULL, role VARCHAR(50) NOT NULL, created_at TIMESTAMP WITH TIME ZONE DEFAULT now())");
        }
    }

    public User save(User user) throws Exception {
        String sql = "INSERT INTO users(name,email,password_hash,role) VALUES(?,?,?,?) RETURNING id";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole().name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    return User.create(id, user.getName(), user.getEmail(), new char[0], user.getRole());
                }
            }
        }
        throw new IllegalStateException("failed to insert user");
    }

    public User findByEmail(String email) throws Exception {
        String sql = "SELECT id,name,email,password_hash,role FROM users WHERE email = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return User.create(rs.getLong("id"), rs.getString("name"), rs.getString("email"), new char[0], Role.valueOf(rs.getString("role")));
                }
            }
        }
        return null;
    }

    public List<User> findAll() throws Exception {
        String sql = "SELECT id,name,email,password_hash,role FROM users ORDER BY id";
        List<User> out = new ArrayList<>();
        try (Connection c = Database.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                out.add(User.create(rs.getLong("id"), rs.getString("name"), rs.getString("email"), new char[0], Role.valueOf(rs.getString("role"))));
            }
        }
        return out;
    }
}
