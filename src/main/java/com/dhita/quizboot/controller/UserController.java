package com.dhita.quizboot.controller;

import com.dhita.quizboot.model.User;
import com.dhita.quizboot.model.UserDto;
import com.dhita.quizboot.service.UserService;
import java.security.GeneralSecurityException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired UserService userService;

  @ModelAttribute("user")
  public UserDto userDto(){
    return new UserDto();
  }

  @GetMapping
  public String getNewUser(Model model){
    return "user";
  }

  @PostMapping
  public String saveUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, HttpServletRequest request){
    if(!userDto.getPassword().equals(userDto.getConfirmpassword())){
      result.rejectValue("username", Errors.NESTED_PATH_SEPARATOR,"Confirm password not equal to  password" );
    }

    User existingUser = userService.findByUsername(userDto.getUsername());
    if(existingUser != null){
      result.rejectValue("username", Errors.NESTED_PATH_SEPARATOR,"Username already exits" );
    }
    if(result.hasErrors()){
      return "user";
    }
    try {
      User user = userService.saveUser(userDto);
      request.login(userDto.getUsername(), userDto.getPassword());
      return "redirect:/play";
    } catch (GeneralSecurityException e) {
      result.rejectValue("password", Errors.NESTED_PATH_SEPARATOR,"Password is not correct" );
      return "user";
    } catch (ServletException e) {
      result.addError(
          new ObjectError("loginError", "Error in login"));
      return "user";
    }
  }

}
