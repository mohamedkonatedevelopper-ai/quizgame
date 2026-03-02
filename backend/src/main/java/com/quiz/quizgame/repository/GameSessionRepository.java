package com.quiz.quizgame.repository;

import com.quiz.quizgame.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSessionRepository extends JpaRepository < GameSession, Long >
{ }
