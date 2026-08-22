package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/*")
public class Main extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        response.setContentType("text/plain");

        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            response.getWriter().write("Página principal");
        } 
        else if (path.equals("/hello")) {
            response.getWriter().write("Hello!");
        } 
        else if (path.equals("/status")) {
            response.getWriter().write("Servidor funcionando!");
        }
    }
}
