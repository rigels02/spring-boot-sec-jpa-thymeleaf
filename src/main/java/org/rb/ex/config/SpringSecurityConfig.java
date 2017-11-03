package org.rb.ex.config;

/**
 *
 * @author raitis
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
// http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
// Switch off the Spring Boot security configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsService userDetailsService;
    
    
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    /**
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */

    
    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	  
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/about").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/h2-console/**").hasAnyRole("ADMIN") /*To get console*/
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        //To show h2 console frame after login and connect
        //must be declared line below
        // ref.: https://stackoverflow.com/questions/40165915/why-does-the-h2-console-in-spring-boot-show-a-blank-screen-after-logging-in
        http.headers().frameOptions().disable();
                
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    	/**
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
                */
        
    	
    	//auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        UserDetailsService udserv = auth.getDefaultUserDetailsService();
        System.out.println("configureGlobal(): UserDetailsService: "+udserv);
        
    }

    /*
    //Spring Boot configured this already.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }*/

}

