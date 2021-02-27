package dev.ericrybarczyk.testablelogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                    authorize
                            .mvcMatchers("/", "/home").permitAll()
                            .mvcMatchers("/h2-console/**").permitAll();
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
                })
                .csrf().ignoringAntMatchers("/h2-console/**");

        // H2 console config
        http.headers().frameOptions().sameOrigin();
    }

    // remove in-memory because H2 is populated by DataInitializer CommandLineRunner
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder().encode("pwd")).roles("CUSTOMER");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
}
