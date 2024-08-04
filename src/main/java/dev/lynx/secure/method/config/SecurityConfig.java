package dev.lynx.secure.method.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

   /* @Bean
    public UserDetailsService userDetailsService() {
        User user1 = new User("user1", "{noop}user1", List.of(new SimpleGrantedAuthority("ROLE_USER")));  // Granted Authority will always have ROLE_ prefix.
        User user2 = new User("admin1", "{noop}admin1", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UserDetails[] userDetails = {user1, user2};
        return new InMemoryUserDetailsManager(userDetails);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
//        new DelegatingPasswordEncoder();
        // pls also check DelegatingPasswordEncoder class for more info.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // BASED ON PREFIX {noop}, {bcrypt} AUTOMATICALLY USES APPROPRIATE ENCODER
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("admin1").password("{noop}admin1").roles("ADMIN")  // WHEN USING BUILDER IT WILL APPEND PREFIX ROLE_
                .build();
        UserDetails user2 = User.builder()
                .username("user1").password("{bcrypt}$2a$10$9rqj.ubsLGiy8YSrmkD.HefAAGghlKoybkoFO56i7DQJsC3XDiWFu").roles("USER") //pwd is user1
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(user1);
        inMemoryUserDetailsManager.createUser(user2);
        return inMemoryUserDetailsManager;
    }
}
