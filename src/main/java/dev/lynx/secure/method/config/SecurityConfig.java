package dev.lynx.secure.method.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    /************************  SETTING FILTER CHAIN *********************/

    //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.csrf(Customizer.withDefaults());
        httpSecurity.sessionManagement(Customizer.withDefaults());
        httpSecurity.userDetailsService(userDetailsService());
        httpSecurity.authorizeHttpRequests(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());

        DefaultSecurityFilterChain filterChain = httpSecurity.build();
        return filterChain;
    }

    /************************  SETTING PASSWORD ENCODER **************************/
    @Bean
    public PasswordEncoder passwordEncoder() {
//        new DelegatingPasswordEncoder("", ); pls also check DelegatingPasswordEncoder class for more info.  https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-updated
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // BASED ON PREFIX {noop}, {bcrypt} AUTOMATICALLY USES APPROPRIATE ENCODER
    }

    /*********************  CREATING USER_DETAILS_SERVICE BEAN ********************/
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

   /* @Bean
    public UserDetailsService userDetailsService() {
        User user1 = new User("user1", "{noop}user1", List.of(new SimpleGrantedAuthority("ROLE_USER")));  // Granted Authority will always have ROLE_ prefix.
        User user2 = new User("admin1", "{noop}admin1", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UserDetails[] userDetails = {user1, user2};
        return new InMemoryUserDetailsManager(userDetails);
    }*/
}
