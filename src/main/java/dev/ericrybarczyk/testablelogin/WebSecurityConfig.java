package dev.ericrybarczyk.testablelogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests(authorize -> {
                    authorize.mvcMatchers("/", "/home").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .formLogin(loginConfigurer -> {
                    loginConfigurer
                            .loginPage("/login").permitAll()
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/securePage")
                            .failureUrl("/home?error");
                })
                .logout(logoutConfigurer -> {
                    logoutConfigurer
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/home?logout")
                            .permitAll();
                });
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("pwd")).roles("CUSTOMER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
