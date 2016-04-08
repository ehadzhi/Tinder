package com.tinder.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.
AbstractAnnotationConfigDispatcherServletInitializer;

import com.tinder.config.chat.WebSocketStompConfig;
import com.tinder.config.security.SecurityConfig;
 
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {};
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringWebConfig.class };
    }
    
    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/", "*.html", "*.pdf" };
    }
}