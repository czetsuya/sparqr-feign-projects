package com.sparqr.cx.iam.web.config;

import com.sparqr.cx.iam.api.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final SecurityProblemSupport securityProblemSupport;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();
    http.cors();

    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        .antMatchers(EndpointConstants.PATH_SERVICES + "/**");

    http.authorizeRequests()
        .anyRequest()
        .permitAll();

    http.exceptionHandling()
        .accessDeniedHandler(securityProblemSupport)
        .authenticationEntryPoint(securityProblemSupport);
  }
}
