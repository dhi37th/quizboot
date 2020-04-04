package com.dhita.quizboot.controller;

import com.dhita.quizboot.model.Category;
import com.dhita.quizboot.model.CategoryDto;
import com.dhita.quizboot.model.Option;
import com.dhita.quizboot.model.Question;
import com.dhita.quizboot.service.CategoryService;
import com.dhita.quizboot.service.QuestionService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

  @Autowired QuestionService questionService;

  @Autowired CategoryService categoryService;

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
  public String getAddNewQuiz(
      @ModelAttribute("question") Question question, Model model, Principal principal) {

    log.info(principal.toString());

    question.getOptions().put(1L, new Option());
    question.getOptions().put(2L, new Option());
    question.getOptions().put(3L, new Option());
    question.getOptions().put(4L, new Option());
    model.addAttribute("question", question);
    model.addAttribute("module", "newquestion");
    model.addAttribute("categories", categoryService.findAll());
    return "question_new";
  }

  @PostMapping("/question")
  public String addNewQuiz(
      @Valid @ModelAttribute("question") Question question,
      BindingResult bindingResult,
      Model model,
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
    questionService.checkQuestionExistsForCategory(question, bindingResult);

    if (bindingResult.hasErrors()) {
      model.addAttribute("categories", categoryService.findAll());
      return "question_new";
    }
    questionService.save(question);
    redirectAttributes.addFlashAttribute("added", true);
    return "redirect:/question";
  }

  @GetMapping("/category")
  public String getCategory(Model model) {
    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setCategories(new ArrayList<Category>(Collections.singletonList(new Category())));
    model.addAttribute("categoryDto", categoryDto);
    return "category";
  }

  @PostMapping("/category")
  public String postCategory(
      @ModelAttribute @Valid CategoryDto categoryDto,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    // TODO: could be done using annotations may be ?
    categoryService.checkCategoryExist(categoryDto, bindingResult);
    if (bindingResult.hasErrors()) {
      return "category";
    }
    categoryService.saveAll(categoryDto);
    redirectAttributes.addFlashAttribute("added", true);
    return "redirect:/category";
  }
}
