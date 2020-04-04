package com.dhita.quizboot.service;

import com.dhita.quizboot.model.Category;
import com.dhita.quizboot.model.CategoryDto;
import com.dhita.quizboot.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  @Autowired CategoryRepository categoryRepository;

  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Optional<Category> findById(Long categoryId) {
    return categoryRepository.findById(categoryId);
  }

  @Override
  public BindingResult checkCategoryExist(CategoryDto categoryDto, BindingResult bindingResult) {
    List<Category> categories = categoryRepository.findAll();
    categories.stream().filter(category -> {
      return categoryDto.getCategories().contains(category);
    }).collect(Collectors.toList()).forEach(category -> {
      log.info("Non unique category "+category.toString());
      bindingResult.addError(new ObjectError("categoryerror", "Category "+category.getName()+" already present"));
    });
    return bindingResult;
  }

  @Override
  public CategoryDto saveAll(CategoryDto categoryDto) {
    List<Category> categoryList = new ArrayList<>();
    categoryDto.getCategories().forEach(cDto -> {
      Category category = new Category();
      category.setName(cDto.getName().toUpperCase());
      categoryList.add(category);
    });
    log.info("Save all categories called with "+categoryList.toString());
    categoryRepository.saveAll(categoryList);
    return categoryDto;
  }

  @Override
  public Category save(Category category) {
    return categoryRepository.saveAndFlush(category);
  }

  @Override
  public Category update(Category category) {
    Category existingCategory = findById(category.getId()).orElseThrow(()->new IllegalArgumentException("Category not found"));
    existingCategory.setName(category.getName());
    return categoryRepository.saveAndFlush(existingCategory);
  }

  @Override
  public void delete(Long categoryId) {
    categoryRepository.deleteById(categoryId);
  }
}
