import {useEffect, useRef, useState} from "react";
import axios from "axios";
import "./game.css";

function Game() {

    /* ==============================
       CONFIG
    ============================== */

    //
    const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";
    console.log("API_URL =", import.meta.env.VITE_API_URL);

    /* ==============================
       STATES
    ============================== */

    const [sessionId, setSessionId] = useState(null);
    const [question, setQuestion] = useState(null);
    const [score, setScore] = useState(0);
    const [timer, setTimer] = useState(15);
    const [message, setMessage] = useState("");
    const [gameOver, setGameOver] = useState(false);
    const [selectedAnswer, setSelectedAnswer] = useState(null);
    const [correctAnswer, setCorrectAnswer] = useState(null);
    const [loading, setLoading] = useState(false);

    const intervalRef = useRef(null);

    /* ==============================
   LOADING ANIMATION
============================== */

    const [loadingText, setLoadingText] = useState(""); // nouveau state pour le texte animé

    useEffect(() => {
        if (!loading) return;

        let dots = 0;
        const interval = setInterval(() => {
            dots = (dots + 1) % 4; // cycle 0 → 1 → 2 → 3 → 0
            setLoadingText("Loading" + ".".repeat(dots));
        }, 500);

        return () => clearInterval(interval);

    }, [loading]);

    /* ==============================
       TIMER
    ============================== */

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

    /* ==============================
       TIMEOUT
    ============================== */

    const handleTimeout = async () => {

        if (!sessionId) return;

        try {

            await axios.post(
                `${API_URL}/api/game/${sessionId}/answer`,
                null,
                {params: {answer: null}}
            );

            await loadNextQuestion(sessionId);

        } catch (err) {

            console.error("Timeout error", err);
            setGameOver(true);

        }
    };

    /* ==============================
       START GAME
    ============================== */

    const startGame = async () => {

        try {

            setLoading(true);

            const response = await axios.post(
                `${API_URL}/api/game/start`
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

    /* ==============================
       LOAD QUESTION
    ============================== */

    const loadNextQuestion = async (id) => {

        try {

            const response = await axios.get(
                `${API_URL}/api/game/${id}/question`
            );

            setQuestion(response.data);
            setTimer(15);
            setSelectedAnswer(null);
            setCorrectAnswer(null);
            setMessage("");

        } catch (err) {

            setGameOver(true);
            setQuestion(null);

        } finally {

            setLoading(false);

        }
    };

    /* ==============================
       SUBMIT ANSWER
    ============================== */

    const submitAnswer = async (answer) => {

        if (!sessionId) return;

        setSelectedAnswer(answer);

        try {

            const response = await axios.post(
                `${API_URL}/api/game/${sessionId}/answer`,
                null,
                {params: {answer}}
            );

            const {score: newScore, message: msg, gameOver: isGameOver} = response.data;

            setScore(newScore);
            setMessage(msg);

            if (msg === "Correct!") {
                setCorrectAnswer(answer);
            }

            setTimeout(() => {

                // 🔥 Toujours mettre à jour le score AVANT transition
                setScore(response.data.score);

                // 🔥 Si dernière question → on affiche d'abord la réponse
                if (isGameOver) {

                    // On attend que l'utilisateur voie le résultat
                    setTimeout(() => {
                        setGameOver(true);
                    }, 700);

                } else {

                    loadNextQuestion(sessionId);
                }

            }, 500);

        } catch (err) {

            console.error("Answer error", err);
            setGameOver(true);

        }
    };

    /* ==============================
       UI
    ============================== */

    return (
        <div className="game-container">
            <div className="game-card">

                {!sessionId ? (

                    <>
                        <h1>ПРОВЕРЬ СВОИ ЗНАНИЯ</h1>

                        <br/>

                        <button
                            className="start-btn"
                            onClick={startGame}
                            disabled={loading}
                        >
                            <h3 className="start-btn-text">
                                {" старт "}
                            </h3>
                        </button>

                        <h3 className="loading">
                            {loading ? loadingText : ""}
                        </h3>
                    </>

                ) : gameOver ? (

                    <>
                        <h1 className="game-over">Game Over</h1>

                        <h2 className="final-score">
                            <h1 className="final-score-letter">Final Score :</h1>
                            <h1 className="final-score-number">{score}/{100}</h1>
                        </h2>

                        <button
                            className="btn-restart"
                            onClick={() => window.location.reload()}
                        >
                            ПЕРЕЗАПУСК
                        </button>
                    </>

                ) : (

                    <>

                        <div className="timer">
                            <div className="score">
                                <p className="score-letter">score</p>
                                <p className="score-number">{score}</p>
                            </div>

                            <div
                                className={`number ${timer < 10 ? "middle" : ""} ${timer < 5 ? "danger" : ""}`}
                            >
                                {timer}
                            </div>
                        </div>

                        {question && (
                            <>
                                <div className="question-container">
                                    <h2>{question.question}</h2>

                                    {question.image_url && (
                                        <img
                                            src={`${API_URL}${question.image_url}`}
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
