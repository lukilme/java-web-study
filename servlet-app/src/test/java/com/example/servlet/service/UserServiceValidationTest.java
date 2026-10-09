package com.example.servlet.service;

import com.example.servlet.model.Role;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceValidationTest {

    @Test
    void shouldRejectDuplicateEmail() throws Exception {
        UserService service = new UserService();
        String email = "duplicate.user@example.com";

        service.createUser("Ana Silva", email, "123456".toCharArray(), Role.USER);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUser("Ana Silva", email, "654321".toCharArray(), Role.USER));

        org.junit.jupiter.api.Assertions.assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void shouldRejectNameWithSpecialCharacters() throws Exception {
        Method method = UserService.class.getDeclaredMethod("validateUserData", String.class, String.class, char[].class, Role.class);
        method.setAccessible(true);

        InvocationTargetException ex = assertThrows(InvocationTargetException.class,
                () -> method.invoke(null, "Ana@Silva", "ana@example.com", "123456".toCharArray(), Role.USER));

        org.junit.jupiter.api.Assertions.assertTrue(ex.getCause() instanceof IllegalArgumentException);
    }

    @Test
    void shouldRejectInvalidEmail() throws Exception {
        Method method = UserService.class.getDeclaredMethod("validateUserData", String.class, String.class, char[].class, Role.class);
        method.setAccessible(true);

        InvocationTargetException ex = assertThrows(InvocationTargetException.class,
                () -> method.invoke(null, "Ana Silva", "invalid-email", "123456".toCharArray(), Role.USER));

        org.junit.jupiter.api.Assertions.assertTrue(ex.getCause() instanceof IllegalArgumentException);
    }

    @Test
    void shouldRejectShortPassword() throws Exception {
        Method method = UserService.class.getDeclaredMethod("validateUserData", String.class, String.class, char[].class, Role.class);
        method.setAccessible(true);

        InvocationTargetException ex = assertThrows(InvocationTargetException.class,
                () -> method.invoke(null, "Ana Silva", "ana@example.com", "12345".toCharArray(), Role.USER));

        org.junit.jupiter.api.Assertions.assertTrue(ex.getCause() instanceof IllegalArgumentException);
    }
}
