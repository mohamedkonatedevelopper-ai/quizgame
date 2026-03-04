package com.quiz.quizgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuizgameApplication
{
	public static void main ( String[] args )
	{
		SpringApplication.run ( QuizgameApplication.class , args );
	}
}
