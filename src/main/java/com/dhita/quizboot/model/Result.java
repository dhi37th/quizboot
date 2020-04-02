package com.dhita.quizboot.model;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result implements Serializable {
  private static final long serialVersionUID = 1;
  private String questionText;
  private String correctAnswer;
  private String selectedAnswer;
  private boolean correct;
}
