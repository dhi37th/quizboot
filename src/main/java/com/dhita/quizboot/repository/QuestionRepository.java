package com.dhita.quizboot.repository;

import com.dhita.quizboot.model.Question;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  List<Question> findAllByCategoryId(Long categoryId);

  Page<Question> findAllByCategoryId(Long category, Pageable page);
}
