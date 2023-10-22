package cn.pledge.envconsole.common.config;


import cn.pledge.envconsole.common.filter.SessionAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SessionAuthenticationFilter sessionAuthenticationFilter;
    private final AuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .antMatcher("/api/**").addFilterBefore(sessionAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(
                        "/api/v1/security/login",
                        "/api/v1/security/logout",
                        "/api/v1/security/loginPre",
                        "/api/v1/security/ipRemove",
                        "/api/v1/security/loginAfter",
                        "/api/v1/user/register",
                        "swagger-resources",
                        "/doc.html"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement()
                .maximumSessions(10)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry())
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().headers().cacheControl();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(false);
//        configuration.addAllowedOrigin(CorsConfiguration.ALL);
//        configuration.addAllowedHeader(CorsConfiguration.ALL);
//        configuration.addAllowedMethod(CorsConfiguration.ALL);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }



    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
