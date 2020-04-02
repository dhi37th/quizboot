package com.dhita.quizboot.service;

import com.dhita.quizboot.model.AnswerDto;
import com.dhita.quizboot.model.Option;
import com.dhita.quizboot.model.Question;
import com.dhita.quizboot.model.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

  @Autowired QuestionService questionService;

  @Override
  public List<Result> checkAnswer(AnswerDto answerDto) {
    List<Result> results = new ArrayList<>();
    Long categoryId = answerDto.getCategoryId();

    answerDto
        .getAnswers()
        .forEach(
            answer -> {
              Question question =
                  questionService.findById(answer.getQuestion().getId()).orElse(null);
              if (question != null) {
                Option correctOption = question.getOptions().values().stream().filter(
                    Option::isCorrect).findFirst().orElse(new Option());
                Option selectedOption = question.getOptions().get(answer.getSelectedOption());
                Result result = new Result();
                result.setQuestionText(question.getText());
                result.setSelectedAnswer(selectedOption.getText());
                result .setCorrectAnswer(correctOption.getText());
                result .setCorrect(selectedOption.isCorrect());
                results.add(result);
              }
            });
    return results;
  }
}
