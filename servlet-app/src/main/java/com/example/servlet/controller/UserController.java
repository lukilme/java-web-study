package com.example.servlet.controller;

import com.example.servlet.model.Role;
import com.example.servlet.model.User;
import com.example.servlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/users/*")
public class UserController extends HttpServlet {

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String roleStr = req.getParameter("role");
        if (name == null || email == null || password == null || roleStr == null) {
            resp.sendError(400, "missing fields");
            return;
        }
        try {
            Role role = Role.valueOf(roleStr.toUpperCase());
            User created = userService.createUser(null, name, email, password.toCharArray(), role);
            resp.setStatus(201);
            resp.getWriter().write("created:" + created.getId());
        } catch (IllegalArgumentException e) {
            resp.sendError(400, "invalid role");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            resp.setContentType("application/json");
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean first = true;
            for (User u : userService.findAll()) {
                if (!first) sb.append(','); else first = false;
                sb.append('{')
                  .append("\"id\":").append(u.getId()).append(',')
                  .append("\"name\":\"").append(u.getName()).append("\",")
                  .append("\"email\":\"").append(u.getEmail()).append("\",")
                  .append("\"role\":\"").append(u.getRole()).append("\"")
                  .append('}');
            }
            sb.append("]");
            resp.getWriter().write(sb.toString());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
