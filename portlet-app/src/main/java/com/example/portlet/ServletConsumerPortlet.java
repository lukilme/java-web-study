package com.example.portlet;

import org.osgi.service.component.annotations.Component;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.Portlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.instanceable=false",
        "javax.portlet.display-name=Auth View",
        "javax.portlet.name=servlet_consumer_portlet",
        "javax.portlet.version=3.0",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)
public class ServletConsumerPortlet extends GenericPortlet {

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        String servletUrl = System.getenv().getOrDefault("SERVLET_URL", "http://localhost:8085/api/db");
        String result;
        try {
            URL url = URI.create(servletUrl).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            int code = conn.getResponseCode();
            InputStream in = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            result = sb.toString();
        } catch (Exception e) {
            result = "Error calling servlet: " + e.getMessage();
        }

        request.setAttribute("servletResponse", result);
        PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/view.jsp");
        if (dispatcher == null) {
            response.getWriter().write("JSP not found: /view.jsp");
            return;
        }
        dispatcher.include(request, response);
    }
}
