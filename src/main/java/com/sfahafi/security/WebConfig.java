package com.sfahafi.security;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer{

	
	
	@Override
    public void addViewControllers(ViewControllerRegistry registro){
        registro.addViewController("/errores/403").setViewName("/errores/403");
    }
	
	
}
