package com.smart.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;



@Configuration
public class myConfig {

    @Bean
    public UserDetailsService getUserDetail()
	{
		return new UserDetailServiceImp();
	}
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authonticationProvider()
    {
    	DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
    	dao.setUserDetailsService(this.getUserDetail());
    	dao.setPasswordEncoder(passwordEncoder());
    	return dao;
    }
    
    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception
	{
        http.authorizeHttpRequests(requests -> requests.requestMatchers("/admin/**").hasRole("ADMIN").
                requestMatchers("/user/**").hasRole("USER").requestMatchers("/**").permitAll().anyRequest().authenticated()).csrf(csrf -> csrf.disable()).
                formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/do_signin")
                        .defaultSuccessUrl("/user/index"));
            
               
      
	return http.build();
    		  
      
      
    		  
	}
    
    
    
}
