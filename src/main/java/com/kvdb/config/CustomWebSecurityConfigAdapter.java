package com.kvdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.kvdb.config.filter.BasicAuthFilter;
import com.kvdb.utils.RestUtils;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	BasicAuthEntryPoint authEntryPoint;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(RestUtils.SYSTEM_USER_NAME)
			.password("{noop}"+RestUtils.SYSTEM_USER_PASSWORD)
			.authorities("SYSTEM_USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/"+RestUtils.SET+"/*", "/"+RestUtils.GET+"/*")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.authenticationEntryPoint(authEntryPoint);
		http.addFilterAfter(new BasicAuthFilter(), BasicAuthenticationFilter.class);
	}

}
