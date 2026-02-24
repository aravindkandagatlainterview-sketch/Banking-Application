package com.BankingApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.BankingApp.ServiceLayer.BankingServiceLayer;

@Configuration
@EnableAutoConfiguration
public class BankingConfig {

	@Autowired
	private BankingServiceLayer bs;

	@Bean
	public PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager aut(AuthenticationConfiguration ac) throws Exception {
		return ac.getAuthenticationManager();
	}
	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {
	    http
	        .cors(Customizer.withDefaults())      
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(req -> req
	            .requestMatchers(
	                "/api/create",
	                "/api/getall",
	                "/api/{accountNumber}",
	                "/api/deposit/**",
	                "/api/withdraw/**",
	                "/api/transfer/**",           
	                "/api/mini_statement/**",
	                "/api/balance/**",
	                "/api/updatePan/**",
	                "/api/login",
	                "/api/me"
	            ).permitAll()
	            .anyRequest().authenticated()
	        )
	        .httpBasic(Customizer.withDefaults())
	        .formLogin(Customizer.withDefaults());

	    return http.build();
	}

	@Bean
	public DaoAuthenticationProvider provider() {
		DaoAuthenticationProvider DAP = new DaoAuthenticationProvider(bs);
		DAP.setPasswordEncoder(pwdEncoder());
		return DAP;

	}

}
