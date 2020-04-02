package com.dhita.quizboot.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto implements Serializable {
  private static final long serialVersionUID = 1;

  List<Answer> answers;

  Long categoryId;
}
