package com.dhita.quizboot.model;

import com.dhita.quizboot.util.OptionAttributeConverter;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "question")
@NoArgsConstructor
public class Question implements Serializable {
  private static final long serialVersionUID = 1;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @NotNull
  @NotEmpty
  private String text;

  @Convert(converter = OptionAttributeConverter.class)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private ConcurrentHashMap<Long, Option> options = new ConcurrentHashMap<>(4);

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Category category;
}
