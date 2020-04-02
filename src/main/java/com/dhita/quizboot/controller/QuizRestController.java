package com.dhita.quizboot.controller;

import com.dhita.quizboot.model.Answer;
import com.dhita.quizboot.model.Category;
import com.dhita.quizboot.model.Question;
import com.dhita.quizboot.service.CategoryService;
import com.dhita.quizboot.service.QuestionService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Utility controller to add new questions quickly :P */
@RestController
@Slf4j
public class QuizRestController {

  @Autowired QuestionService questionService;

  @Autowired CategoryService categoryService;

  @GetMapping("/quizquestions")
  public List<Question> getAllQuestionRest(
      @RequestParam(value = "category", required = false) Long categoryId) {
    return questionService.findAll();
  }

  @PostMapping(
      value = "/quizquestions",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Question> saveAllQuestions(@RequestBody List<Question> questions) {
    return questionService.saveAll(questions);
  }

  // Should be included in its own file if more methods are included! SOLID
  @PostMapping(
      value = "/quizquestions/category",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Category saveCategory(@RequestBody Category category) {
    return categoryService.save(category);
  }

  @PostMapping(
      value = "/quizquestions/category/{categoryId}/answer",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public void checkAnswer(
      @RequestBody Answer answers, @PathVariable(value = "categoryId") Long categoryId) {
    log.info("Answer List : " + answers);
  }
}
