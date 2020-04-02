package com.dhita.quizboot.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer implements Serializable {
  private static final long serialVersionUID = 1;

  private Question question;

  private Long selectedOption;
}
