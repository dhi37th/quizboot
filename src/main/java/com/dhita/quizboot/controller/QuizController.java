package com.dhita.quizboot.controller;

import com.dhita.quizboot.model.Answer;
import com.dhita.quizboot.model.AnswerDto;
import com.dhita.quizboot.model.Option;
import com.dhita.quizboot.model.Question;
import com.dhita.quizboot.model.Result;
import com.dhita.quizboot.service.AnswerService;
import com.dhita.quizboot.service.CategoryService;
import com.dhita.quizboot.service.QuestionService;
import com.dhita.quizboot.util.AnswerUtility;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@PreAuthorize("isAuthenticated()")
public class QuizController {

  @Autowired QuestionService questionService;

  @Autowired CategoryService categoryService;

  @Autowired AnswerService answerService;

  @PreAuthorize("permitAll()")
  @RequestMapping("/login")
  public String getLogin() {
    return "login";
  }

  @PreAuthorize("permitAll()")
  @GetMapping({"/", "/play"})
  public String getPlayQuiz(Model model, @RequestParam("category") Optional<Long> category) {
    model.addAttribute("categories", categoryService.findAll());
    if (category.isPresent()) {
      model.addAttribute("module", "play");
      List<Answer> answers =
          AnswerUtility.createAnswerList(questionService.findAll(category.get()));
      AnswerDto answerDto = new AnswerDto(answers, category.get());
      model.addAttribute("answerDto", answerDto);
      return "play";
    } else {
      model.addAttribute("module", "play");
      return "home";
    }
  }

  @PostMapping("/play")
  public String checkQuiz(Model model, @ModelAttribute("answerDto") AnswerDto answerDto) {
    answerDto.getAnswers().forEach(answer -> log.info(answer.toString()));
    List<Result> results = answerService.checkAnswer(answerDto);
    model.addAttribute("results", results);
    model.addAttribute("score", results.stream().filter(Result::isCorrect).count());
    return "result";
  }

}
