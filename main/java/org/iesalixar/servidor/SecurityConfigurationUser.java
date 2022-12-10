package org.iesalixar.servidor;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
@EnableWebSecurity
public class SecurityConfigurationUser extends WebSecurityConfigurerAdapter{

	/*
	 * MÉTODO PARA ESTABLECER AUTORIZACION - A QUÉ PUEDO ACCEDER
	 */
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*http
		.csrf().disable()
	      .authorizeRequests()
	      .antMatchers("/admin/**").hasRole("ADMIN")
	      .antMatchers("/anonymous*").anonymous()
	      .antMatchers("/").permitAll()	      
	      .antMatchers("/register").not().authenticated()
	      .anyRequest().authenticated()
		.and()
	      .formLogin()
	      .loginPage("/login")
	      .loginProcessingUrl("/login")
	      .defaultSuccessUrl("/index", true)
	      .and()
	      .formLogin()
	      .loginPage("/admin")
	      .loginProcessingUrl("/admin")
	      .defaultSuccessUrl("/admin/index", true)
	      .and()
	      .logout()
	      .logoutUrl("/close")
	      .deleteCookies("JSESSIONID");*/
		
		http.antMatcher("/index*")
        .authorizeRequests()
        .anyRequest()
        .permitAll()
        
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .failureUrl("/login?error=loginError")
        .defaultSuccessUrl("/index")
        
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout=true")
        .deleteCookies("JSESSIONID")
        
        .and()
        .exceptionHandling()
        .accessDeniedPage("/403")
        
        .and()
        .csrf().disable();
		
	}		
}