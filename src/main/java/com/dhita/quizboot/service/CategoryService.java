package com.dhita.quizboot.service;

import com.dhita.quizboot.model.Category;
import com.dhita.quizboot.model.CategoryDto;
import java.util.List;
import java.util.Optional;
import org.springframework.validation.BindingResult;

public interface CategoryService {
  /**
   * Find all
   *
   * @return List
   */
  List<Category> findAll();

  /**
   * Find category by id
   *
   * @param categoryId category id
   * @return Optional
   */
  Optional<Category> findById(Long categoryId);

  /**
   * Save category
   *
   * @param category category
   * @return Category
   */
  Category save(Category category);

  /**
   * Check if category already exits
   * @param categoryDto catrgoryDto
   * @param bindingResult binding result
   * @return
   */
  void checkCategoryExist(CategoryDto categoryDto, BindingResult bindingResult);

  /**
   * Save multiple category together
   * @param categoryDto CategoryDto
   * @return CategoryDto
   */
  CategoryDto saveAll(CategoryDto categoryDto);

  /**
   * Update category
   *
   * @param category category
   * @return Category
   */
  Category update(Category category);

  /**
   * Delete category
   *
   * @param categoryId category id
   */
  void delete(Long categoryId);
}
