package com.example.portlet;

import org.osgi.service.component.annotations.Component;

import java.io.IOException;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=Auth View",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/login.jsp",
        "javax.portlet.name=auth_view",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)
public class AuthViewPortlet extends GenericPortlet{
    @Override
    protected void doView(
            RenderRequest request,
            RenderResponse response)
            throws IOException {

        response.getWriter().write("PORTLET OK");
    }

}
