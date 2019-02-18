package com.configuration;

import org.camunda.*;
import org.camunda.connect.plugin.impl.ConnectProcessEnginePlugin;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebCamundaConfiguration  extends WebMvcConfigurerAdapter{

//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//		return bean;
//	}
//	
//	@Bean
//	public ConnectProcessEnginePlugin connectProcessEnginePlugin() {
//	  return new ConnectProcessEnginePlugin();
//	}
	
	
 

	    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
	            "classpath:/META-INF/resources/", "classpath:/resources/",
	            "classpath:/static/", "classpath:/public/"  };

	    
	 
	    @Override
	    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    	registry.addResourceHandler("/**")
            .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	    	
	        registry.addResourceHandler("/**").addResourceLocations("file:///C:/elastic_repo/");
	    }
}