package com.quiz.quizgame.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class GameSession
{

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	private long id;

	private int score;

	private int currentQuestionIndex;

	private LocalDateTime questionStartTime;

	private boolean finished;

	// Getters and setters
}
