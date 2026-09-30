<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.portlet.PortletRequest" %>
<%
    String resp = (String) request.getAttribute("servletResponse");
    if (resp == null) resp = "No response from servlet";
    String esc = resp.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Servlet Consumer</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0.5rem; }
        pre { background:#f6f8fa; padding:0.5rem; border-radius:4px; }
    </style>
</head>
<body>
    <h3>Servlet Consumer Portlet</h3>
    <pre style="white-space:pre-wrap;"><%= esc %></pre>
</body>
</html>
