package dev.ericrybarczyk.testablelogin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        log.info("******* Starting data initializer process...");

        User user = new User();
        user.setFirstName("Firsty");
        user.setLastName("McLasty");
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("pwd"));
        userRepository.save(user);


        log.debug("Users Loaded: " + userRepository.count());
        log.info("******* Finished data initializer process...");
    }

}
