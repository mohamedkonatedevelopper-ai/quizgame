import {useEffect, useRef, useState} from "react";
import axios from "axios";
import "./game.css";

function Game() {

    const [playerName, setPlayerName] = useState("");
    const [sessionId, setSessionId] = useState(null);
    const [question, setQuestion] = useState(null);
    const [score, setScore] = useState(0);
    const [timer, setTimer] = useState(15);
    const [message, setMessage] = useState("");
    const [gameOver, setGameOver] = useState(false);
    const [selectedAnswer, setSelectedAnswer] = useState(null);
    const [correctAnswer, setCorrectAnswer] = useState(null);

    const intervalRef = useRef(null);

    /* =====================================================
       TIMER
    ===================================================== */

    useEffect(() => {

        if (!question || gameOver) return;

        setTimer(15);

        if (intervalRef.current)
            clearInterval(intervalRef.current);

        let timeLeft = 15;

        intervalRef.current = setInterval(() => {

            timeLeft -= 1;
            setTimer(timeLeft);

            if (timeLeft <= 0) {
                clearInterval(intervalRef.current);
                handleTimeout();
            }

        }, 1000);

        return () => {
            if (intervalRef.current)
                clearInterval(intervalRef.current);
        };

    }, [question]);

    /* =====================================================
       TIMEOUT
    ===================================================== */

    const handleTimeout = async () => {

        if (!sessionId) return;

        try {

            await axios.post(
                `http://localhost:8080/api/game/${sessionId}/answer`,
                null,
                {params: {answer: null}}
            );

            await loadNextQuestion(sessionId);

        } catch (err) {
            console.error("Timeout error", err);
            setGameOver(true);
        }
    };

    /* =====================================================
       START GAME
    ===================================================== */

    const startGame = async () => {

        try {

            const response = await axios.post(
                `http://localhost:8080/api/game/start?playerName=${playerName}`
            );

            const id = response.data.id;

            setSessionId(id);
            setScore(0);
            setGameOver(false);

            await loadNextQuestion(id);

        } catch (err) {
            console.error("Start error", err);
        }
    };

    /* =====================================================
       LOAD QUESTION
    ===================================================== */

    const loadNextQuestion = async (id) => {

        try {

            const response = await axios.get(
                `http://localhost:8080/api/game/${id}/question`
            );

            setQuestion(response.data);
            setTimer(15);
            setSelectedAnswer(null);
            setCorrectAnswer(null);
            setMessage("");

        } catch (err) {
            // Si backend retourne Game Over
            setGameOver(true);
            setQuestion(null);
        }
    };

    /* =====================================================
       SUBMIT ANSWER
    ===================================================== */

    const submitAnswer = async (answer) => {

        setSelectedAnswer(answer);

        try {

            const response = await axios.post(
                `http://localhost:8080/api/game/${sessionId}/answer`,
                null,
                {params: {answer}}
            );

            setScore(response.data.score);
            setMessage(response.data.message);

            if (response.data.message === "Correct!") {
                setCorrectAnswer(answer);
            }

            // ⛔ SUPPRIME le return immédiat
            // On gère gameOver après le délai

            setTimeout(() => {

                if (response.data.gameOver) {
                    setGameOver(true);
                } else {
                    loadNextQuestion(sessionId);
                }

            }, 1000);

        } catch (err) {
            setGameOver(true);
        }
    };

    /* =====================================================
       UI
    ===================================================== */

    return (
        <div className="game-container">
            <div className="game-card">

                {!sessionId ? (

                    <>
                        <h1>WELCOME TO THE QUIZ GAME</h1>

                        <br/>

                        <button className="start-btn" onClick={startGame}>
                            <h3 className="start-btn-text"> {">"}</h3>
                        </button>
                    </>

                ) : gameOver ? (

                    <>
                        <h1 className="game-over">Game Over</h1>
                        <h2 className="final-score">Final Score: {score}</h2>
                        <button className="btn-restart" onClick={() => window.location.reload()}>
                            RESTART
                        </button>
                    </>

                ) : (

                    <>

                        <div className="timer">
                            <div className="score">
                                <p className="score-letter">score</p>
                                <p className="score-number">{score}</p>
                            </div>
                            <div className={`number ${timer < 10 ? "middle" : ""} ${timer < 5 ? "danger" : ""}`}>
                                {timer}
                            </div>
                        </div>

                        {question && (
                            <>
                                <div className="question-container">
                                    <h2>{question.question}</h2>

                                    {question.image_url && (
                                        <img
                                            src={`http://localhost:8080/${question.image_url}`}
                                            alt="question"
                                            className="question-image"
                                        />
                                    )}
                                </div>
                                {["option_1", "option_2", "option_3", "option_4"].map((key) => (
                                    <button
                                        key={key}
                                        className={`answer-btn
            ${selectedAnswer === question[key] && correctAnswer === question[key] ? "correct" : ""}
            ${selectedAnswer === question[key] && correctAnswer !== question[key] ? "wrong" : ""}
        `}
                                        onClick={() => submitAnswer(question[key])}
                                        disabled={selectedAnswer !== null}
                                    >
                                        {question[key]}
                                    </button>
                                ))}
                            </>
                        )}

                        <div className="message">
                            {message}
                        </div>

                    </>
                )}

            </div>
        </div>
    );
}

export default Game;
