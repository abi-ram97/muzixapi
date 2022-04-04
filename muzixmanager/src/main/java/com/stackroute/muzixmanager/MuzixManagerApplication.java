package com.stackroute.muzixmanager;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.stackroute.muzixmanager.filter.JwtFilter;

@SpringBootApplication
public class MuzixManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuzixManagerApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<Filter> jwtFilter() {
		final FilterRegistrationBean<Filter> registerBean = new FilterRegistrationBean<Filter>();
		registerBean.setFilter(new JwtFilter());
		registerBean.addUrlPatterns("/api/*");
		return registerBean;
	}

}
