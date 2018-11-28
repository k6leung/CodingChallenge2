package com.coding.task.security.no.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//for simple simulation only...

@Configuration
@EnableWebSecurity
public class NoSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public NoSecurityConfiguration() {
		super();
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/api/*").permitAll();
		
	}
}
