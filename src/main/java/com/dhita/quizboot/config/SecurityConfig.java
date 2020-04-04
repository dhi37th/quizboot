package com.dhita.quizboot.config;

import com.dhita.quizboot.service.UserService;
import com.dhita.quizboot.util.EncryptionUtil;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String LOGIN_URL = "/login";
  @Autowired UserService userService;
  @Autowired EncryptionUtil encryptionUtil;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers("/login", "/js/**", "/css/**", "/img/**", "/webjars/**")
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/h2-console/**")
        .permitAll()
        .and()
        .headers()
        .frameOptions()
        .disable();

    http.authorizeRequests()
        .anyRequest()
        .permitAll() //method level authentication is in place
        .and()
        .formLogin()
        .loginPage(LOGIN_URL)
        .loginProcessingUrl("/perform_login")
        .defaultSuccessUrl("/")
        .failureUrl("/login?error")
        .usernameParameter("username")
        .passwordParameter("password")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence rawPassword) {
        try {
          return encryptionUtil.encrypt(rawPassword.toString());
        } catch (GeneralSecurityException e) {
          return null;
        }
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
          return rawPassword.toString().equals(encryptionUtil.decrypt(encodedPassword));
        } catch (GeneralSecurityException e) {
          return false;
        }
      }
    };
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }
}
