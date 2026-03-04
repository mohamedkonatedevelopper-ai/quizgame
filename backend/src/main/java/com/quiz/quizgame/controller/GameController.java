package com.quiz.quizgame.controller;

import com.quiz.quizgame.model.GameSession;
import com.quiz.quizgame.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping ( "/api/game" )
@CrossOrigin ( origins = "http://localhost:5173" )
public class GameController
{

	private final GameService service;

	public GameController ( GameService service )
	{
		this.service = service;
	}

	@PostMapping ( "/start" )
	public ResponseEntity < GameSession > start ()
	{
		return ResponseEntity.ok ( service.startGame () );
	}

	@GetMapping ( "/{sessionId}/question" )
	public ResponseEntity < ? > getQuestion ( @PathVariable long sessionId )
	{

		try
		{
			return ResponseEntity.ok ( service.getQuestion ( sessionId ) );
		}
		catch ( RuntimeException e )
		{
			return ResponseEntity.badRequest ().body ( e.getMessage () );
		}
	}

	@PostMapping ( "/{sessionId}/answer" )
	public ResponseEntity < Map < String, Object > > answer ( @PathVariable long sessionId , @RequestParam ( required = false ) String answer )
	{

		return ResponseEntity.ok ( service.submitAnswer ( sessionId , answer ) );
	}
}
