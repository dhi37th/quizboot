package com.dhita.quizboot.service;

import com.dhita.quizboot.model.Category;
import com.dhita.quizboot.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
