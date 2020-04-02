package com.dhita.quizboot.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option implements Serializable {
  private static final long serialVersionUID = 1;
  private String text;
  private boolean correct;
}
