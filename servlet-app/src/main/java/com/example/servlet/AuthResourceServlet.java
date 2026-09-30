package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthResourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String contextLink1 = "/o/portlet-app/login.jsp"; // Liferay OSGi module path
        String contextLink2 = "/o/portlet-app/register.jsp";
        String altLogin = "/login.jsp"; // alternative root paths
        String altRegister = "/register.jsp";

        String html = "<!doctype html><html><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">" +
                "<title>Auth</title><script src=\"https://cdn.tailwindcss.com\"></script></head><body class=\"bg-gray-50 min-h-screen flex items-center justify-center\">" +
                "<div class=\"max-w-md w-full bg-white p-8 rounded-lg shadow\">" +
                "<h2 class=\"text-2xl font-semibold mb-4\">Acessar autenticação</h2>" +
                "<ul class=\"space-y-2\">" +
                "<li><a href=\"" + contextLink1 + "\" class=\"text-indigo-600 hover:underline\">Login (portlet)</a></li>" +
                "<li><a href=\"" + contextLink2 + "\" class=\"text-indigo-600 hover:underline\">Register (portlet)</a></li>" +
                "<li><a href=\"" + altLogin + "\" class=\"text-indigo-600 hover:underline\">Login (root)</a></li>" +
                "<li><a href=\"" + altRegister + "\" class=\"text-indigo-600 hover:underline\">Register (root)</a></li>" +
                "</ul></div></body></html>";

        resp.getWriter().write(html);
    }
}
