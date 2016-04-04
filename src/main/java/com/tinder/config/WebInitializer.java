package com.tinder.config;

import org.springframework.web.servlet.support.
AbstractAnnotationConfigDispatcherServletInitializer;

import com.tinder.config.chat.WebSocketStompConfig;
 
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { BeanConfig.class,
        		SecurityConfig.class,WebSocketStompConfig.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringWebConfig.class };
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/", "*.html", "*.pdf" };
    }
}