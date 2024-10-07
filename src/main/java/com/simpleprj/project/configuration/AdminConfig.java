package com.simpleprj.project.configuration;

import com.simpleprj.project.entity.User;
import com.simpleprj.project.enums.Roles;
import com.simpleprj.project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminConfig {

    private static final Logger log = LoggerFactory.getLogger(AdminConfig.class);
    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(prefix = "string", value = "datasource.driverClassName"
            , havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Roles.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        //.roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password");
            }
        };
    }
}
