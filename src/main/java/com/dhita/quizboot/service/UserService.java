package com.dhita.quizboot.service;

import com.dhita.quizboot.model.User;
import com.dhita.quizboot.model.UserDto;
import java.security.GeneralSecurityException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User findByUsername(String username);
  User saveUser(UserDto userDto) throws GeneralSecurityException;
}
