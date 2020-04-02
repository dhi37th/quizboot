package com.dhita.quizboot.service;

import com.dhita.quizboot.model.Category;
import java.util.List;
import java.util.Optional;

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
