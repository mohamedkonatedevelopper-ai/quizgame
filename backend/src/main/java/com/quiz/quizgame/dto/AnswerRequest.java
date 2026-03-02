package com.quiz.quizgame.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest
{

	private long   questionId;
	private String selectedAnswer;
}
