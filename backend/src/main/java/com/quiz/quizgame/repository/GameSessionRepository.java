package com.quiz.quizgame.repository;

import com.quiz.quizgame.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GameSessionRepository extends JpaRepository < GameSession, Long >
{

	// 🔥 Méthode pour supprimer les sessions terminées + anciennes

	List < GameSession > findByFinishedTrueAndQuestionStartTimeBefore ( LocalDateTime time );
}
