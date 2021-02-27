package dev.ericrybarczyk.testablelogin;

import org.springframework.security.test.context.support.WithSecurityContext;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Docs: https://docs.spring.io/spring-security/site/docs/current/reference/html5/#test-method-withsecuritycontext

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String username() default "user";
    String password() default "pwd";
    String firstName() default "Firstname";
    String lastName() default "Lastname";
}
