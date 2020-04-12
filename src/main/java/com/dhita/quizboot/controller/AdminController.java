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
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
      @RequestParam("size") Optional<Integer> size,
      @RequestParam("category") Optional<Long> category) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(8);
    if (category.isPresent()) {
      model.addAttribute(
          "questions", questionService.findAll(category.get(), currentPage - 1, pageSize));
    } else {
      model.addAttribute("questions", questionService.findAll(currentPage - 1, pageSize));
    }
    model.addAttribute("module", "allquestions");
    model.addAttribute("categories", categoryService.findAll());
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
    questionService.checkQuestionExistsForCategory(question, bindingResult);
    if (bindingResult.hasErrors()) {
      model.addAttribute("categories", categoryService.findAll());
      return "question_new";
    }
    questionService.save(question);
    redirectAttributes.addFlashAttribute("added", true);
    return "redirect:/question";
  }

  @PostMapping("/question/delete")
  public String deleteQuestion(
      @ModelAttribute(value = "quesToDelete") Question quesToDelete,
      RedirectAttributes redirectAttributes,
      @RequestParam("page") Optional<Integer> page) {
    int currentPage = page.orElse(1);
    questionService.delete(quesToDelete.getId());
    redirectAttributes.addFlashAttribute("questionDeleted", true);
    return "redirect:/questions?page=" + currentPage;
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
