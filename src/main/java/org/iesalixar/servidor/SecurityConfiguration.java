package org.iesalixar.servidor;

import org.iesalixar.servidor.services.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*
 * CLASE DONDE ESTABLECEREMOS LA CONFIGURACION DE
 * AUTENTIFICACION - CÓMO ACCEDO
 * AUTORIZACION - A QUÉ PUEDO ACCEDER
 * MÉTODO DE ENCRIPTACIÓN DE LAS CONTRASEÑAS
 */
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(2)
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	
	/* Obtengo una refencia al SINGLENTON del userDetailsService	 * 
	 */
	@Autowired
	JPAUserDetailsService userDetailsService;

	/*
	 * ESTABLECEMOS EL PASSWORD ENCODER. FUERZA 15 (de 4 a 31)
	 */
	@Bean
    public PasswordEncoder getPasswordEncoder() {         
		return new BCryptPasswordEncoder(15);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

	/*
	 * MÉTODO PARA ESTABLECER AUTORIZACION - A QUÉ PUEDO ACCEDER
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.antMatcher("/admin*")
        .authorizeRequests()
        .anyRequest()
        .hasRole("ADMIN")
        
        .and()
        .formLogin()
        .loginPage("/admin")
        .loginProcessingUrl("/admin_login")
        .failureUrl("/admin?error=loginError")
        .defaultSuccessUrl("/admin/index")
        .permitAll()
        
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/admin?logout=true")
        .deleteCookies("JSESSIONID")
        
        .and()
        .exceptionHandling()
        .accessDeniedPage("/403")
        
        .and()
        .csrf().disable();
		
	}	
	
	
}
