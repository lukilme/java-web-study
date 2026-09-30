package com.example.portlet;

import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

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
public class AuthViewPortlet extends MVCPortlet {

    @Override
    public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        String page = request.getRenderParameters().getValue("page");
        if (page == null || page.isEmpty()) page = "login";
        String jsp = "login".equals(page) ? "/login.jsp" : "/register.jsp";
        PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher(jsp);
        dispatcher.include(request, response);
    }
}
