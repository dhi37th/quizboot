package com.dhita.quizboot.controller;

import com.dhita.quizboot.model.Answer;
import com.dhita.quizboot.model.AnswerDto;
import com.dhita.quizboot.model.Category;
import com.dhita.quizboot.model.Option;
import com.dhita.quizboot.model.Question;
import com.dhita.quizboot.model.Result;
import com.dhita.quizboot.service.AnswerService;
import com.dhita.quizboot.service.CategoryService;
import com.dhita.quizboot.service.QuestionService;
import com.dhita.quizboot.util.AnswerUtility;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class QuizController {

  @Autowired QuestionService questionService;

  @Autowired CategoryService categoryService;

  @Autowired AnswerService answerService;

  @ModelAttribute("categories")
  public List<Category> populateCategory() {
    return categoryService.findAll();
  }

  @GetMapping({"/", "/play"})
  public String getPlayQuiz(Model model, @RequestParam("category") Optional<Long> category) {
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
    log.info("category : " + answerDto.getCategoryId().toString());
    answerDto.getAnswers().forEach(answer -> log.info(answer.toString()));
    List<Result> results = answerService.checkAnswer(answerDto);
    model.addAttribute("results", results);
    model.addAttribute("score", results.stream().filter(Result::isCorrect).count());
    return "result";
  }

  @GetMapping({"/questions"})
  public String getHome(
      Model model,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(8);
    model.addAttribute("questions", questionService.findAll(currentPage - 1, pageSize));
    model.addAttribute("module", "allquestions");
    return "question_list";
  }

  @GetMapping({"/question"})
  public String getAddNewQuiz(@ModelAttribute("question") Question question, Model model) {
    question.getOptions().put(1L, new Option());
    question.getOptions().put(2L, new Option());
    question.getOptions().put(3L, new Option());
    question.getOptions().put(4L, new Option());
    model.addAttribute("question", question);
    model.addAttribute("module", "newquestion");
    return "question_new";
  }

  @PostMapping("/question")
  public String addNewQuiz(
      @Valid @ModelAttribute("question") Question question,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    long correctCount =
        question.getOptions().entrySet().stream()
            .filter(
                integerOptionEntry -> {
                  return integerOptionEntry.getValue().isCorrect();
                })
            .count();
    if (correctCount > 1) {
      bindingResult.addError(new ObjectError("correctCount", "Only one option can be correct"));
    } else if (correctCount < 1) {
      bindingResult.addError(
          new ObjectError("correctCount", "Atleast one option should be correct"));
    }
    if (bindingResult.hasErrors()) {
      return "question_new";
    }
    questionService.save(question);
    redirectAttributes.addFlashAttribute("added", true);
    return "redirect:/question";
  }
}
