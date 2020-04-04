package com.dhita.quizboot.service;

import com.dhita.quizboot.model.Question;
import com.dhita.quizboot.repository.QuestionRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

  @Autowired QuestionRepository questionRepository;

  @Override
  public void checkQuestionExistsForCategory(Question question, BindingResult bindingResult) {
    List<Question> questionList =
        questionRepository.findAllByCategoryId(question.getCategory().getId());
    boolean questionExists =
        questionList.stream()
            .anyMatch(
                existingQuestion ->
                    existingQuestion.getText().equalsIgnoreCase(question.getText()));
    if (questionExists) {
      bindingResult.addError(
          new ObjectError(
              "questionerror",
              "Question already present in category " + question.getCategory().getName()));
    }
  }

  @Override
  public List<Question> saveAll(List<Question> questions) {
    return questionRepository.saveAll(questions);
  }

  @Override
  public Page<Question> findAll(int pageNumber, int size) {
    Pageable page = PageRequest.of(pageNumber, size);
    return questionRepository.findAll(page);
  }

  @Override
  public List<Question> findAll(Long categoryId) {
    return questionRepository.findAllByCategoryId(categoryId);
  }

  @Override
  public List<Question> findAll() {
    return questionRepository.findAll();
  }

  @Override
  public Optional<Question> findById(Long questionId) {
    return questionRepository.findById(questionId);
  }

  @Override
  public Question save(Question question) {
    return questionRepository.saveAndFlush(question);
  }

  @Override
  public Question update(Question question) {
    Question existingQuestion =
        questionRepository
            .findById(question.getId())
            .orElseThrow(() -> new IllegalArgumentException("Question not found"));
    existingQuestion.setText(question.getText());
    existingQuestion.setOptions(question.getOptions());
    existingQuestion.setCategory(question.getCategory());
    return questionRepository.save(existingQuestion);
  }

  @Override
  public void delete(Long questionId) {
    questionRepository.deleteById(questionId);
  }
}
