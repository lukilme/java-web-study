package com.example.servlet.service;

import com.example.servlet.repo.UserRepository;
import com.example.servlet.model.User;
import com.example.servlet.model.Role;
import java.util.List;

public class UserService {

    private final UserRepository repo = new UserRepository();

    public UserService() throws Exception {
        try {
            org.flywaydb.core.Flyway flyway = org.flywaydb.core.Flyway.configure()
                    .dataSource(System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://db:5432/postgres"),
                            System.getenv().getOrDefault("DB_USER", "postgres"),
                            System.getenv().getOrDefault("DB_PASSWORD", "postgres"))
                    .locations("classpath:db/migration")
                    .load();
            flyway.migrate();
        } catch (Throwable t) {
            repo.migrate();
        }
    }

    public User createUser(String name, String email, char[] password, Role role) throws Exception {
        String normalizedEmail = normalizeEmail(email);
        validateUserData(name, normalizedEmail, password, role);

        if (repo.findByEmail(normalizedEmail) != null) {
            throw new IllegalArgumentException("email already exists");
        }

        User u = User.create(null, name.trim(), normalizedEmail, password, role);
        return repo.save(u);
    }

    private static String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        return email.trim().toLowerCase();
    }

    static void validateUserData(String name, String email, char[] password, Role role) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name is required");
        }
        if (!name.trim().matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$")) {
            throw new IllegalArgumentException("name must contain only letters and spaces");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("email is required");
        }
        if (!email.trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("email must contain a valid format");
        }

        if (password == null || password.length == 0 || new String(password).trim().isEmpty()) {
            throw new IllegalArgumentException("password cannot be empty");
        }
        if (new String(password).trim().length() < 6) {
            throw new IllegalArgumentException("password must have at least 6 characters");
        }

        if (role == null) {
            throw new IllegalArgumentException("role is required");
        }
    }

    public User findByEmail(String email) throws Exception {
        return repo.findByEmail(email);
    }

    public List<User> findAll() throws Exception {
        return repo.findAll();
    }
}
