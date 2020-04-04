package com.dhita.quizboot.model;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
  @NotEmpty  private String name;
  @NotEmpty  private String username;
  @NotEmpty  private String password;
  @NotEmpty  private String confirmpassword;

}
