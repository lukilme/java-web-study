package com.example.servlet.init;

import com.example.servlet.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            UserService userService = new UserService();
            sce.getServletContext().setAttribute("userService", userService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize UserService", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // nothing to do for while
    }
}
