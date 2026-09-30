package com.example.servlet.controller;

import com.example.servlet.model.User;
import com.example.servlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/auth/login")
public class AuthApiServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        Object svc = getServletContext().getAttribute("userService");
        if (svc instanceof UserService) {
            this.userService = (UserService) svc;
        } else {
            try {
                this.userService = new UserService();
                getServletContext().setAttribute("userService", this.userService);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        resp.setContentType("application/json;charset=UTF-8");
        if (email == null || password == null) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"missing_credentials\"}");
            return;
        }
        try {
            User user = userService.findByEmail(email);
            if (user == null || !user.verifyPassword(password.toCharArray())) {
                resp.setStatus(401);
                resp.getWriter().write("{\"error\":\"invalid_credentials\"}");
                return;
            }
            String json = String.format("{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"role\":\"%s\"}",
                    user.getId(), escapeJson(user.getName()), escapeJson(user.getEmail()), user.getRole().name());
            resp.getWriter().write(json);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static String escapeJson(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
