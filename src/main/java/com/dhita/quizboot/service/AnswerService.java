package com.dhita.quizboot.service;

import com.dhita.quizboot.model.AnswerDto;
import com.dhita.quizboot.model.Result;
import java.util.List;

public interface AnswerService {
  List<Result> checkAnswer(AnswerDto answerDto);
}
