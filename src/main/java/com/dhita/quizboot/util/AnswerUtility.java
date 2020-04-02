package com.dhita.quizboot.util;

import com.dhita.quizboot.model.Answer;
import com.dhita.quizboot.model.Question;
import java.util.ArrayList;
import java.util.List;

public class AnswerUtility {

  public static List<Answer> createAnswerList(List<Question> questionList) {
    List<Answer> answerList = new ArrayList<>(questionList.size());
    questionList.forEach(
        question -> {
          answerList.add(new Answer(question, -1L));
        });
    return answerList;
  }
}
