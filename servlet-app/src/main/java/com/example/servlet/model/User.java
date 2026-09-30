package com.example.servlet.model;

import com.example.servlet.util.PasswordUtil;
import java.util.Objects;

public final class User {

    private final Long id;
    private final String name;
    private final String email;
    private final String passwordHash;
    private final Role role;

    private User(Long id, String name, String email, String passwordHash, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public static User create(Long id, String name, String email, char[] password, Role role) {
        Objects.requireNonNull(name, "name is required");
        Objects.requireNonNull(email, "email is required");
        Objects.requireNonNull(password, "password is required");
        Objects.requireNonNull(role, "role is required");
        String hash = PasswordUtil.hash(password);
        return new User(id, name.trim(), email.trim().toLowerCase(), hash, role);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }

    public boolean verifyPassword(char[] password) {
        return PasswordUtil.verify(password, this.passwordHash);
    }

}
