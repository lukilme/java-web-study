package com.example.servlet.service;

import com.example.servlet.repo.UserRepository;
import com.example.servlet.model.User;

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

    public User createUser(Long id, String name, String email, char[] password, com.example.servlet.model.Role role) throws Exception {
        User u = User.create(id, name, email, password, role);
        return repo.save(u);
    }

    public User findByEmail(String email) throws Exception {
        return repo.findByEmail(email);
    }

    public List<User> findAll() throws Exception {
        return repo.findAll();
    }
}
