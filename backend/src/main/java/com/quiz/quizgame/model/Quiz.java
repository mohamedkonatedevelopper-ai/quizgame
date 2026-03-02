package com.quiz.quizgame.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz
{
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	private long question_id;

	private String question;

	private String option_1;
	private String option_2;
	private String option_3;
	private String option_4;

	private String image_url;

	private String answer;
}
