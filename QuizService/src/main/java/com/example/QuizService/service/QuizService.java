package com.example.QuizService.service;

import com.example.QuizService.feign.QuizInterface;
import com.example.QuizService.model.QuestionDto;
import com.example.QuizService.model.Quiz;
import com.example.QuizService.model.Response;
import com.example.QuizService.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizInterface quizInterface;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.createQuestionsForUser(category,numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionDto>> getQuiz(int id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        List<Integer> questionIds =  quiz.getQuestionIds();

        return quizInterface.getQuestionsByID(questionIds);
    }

    public ResponseEntity<Integer> submitQuiz(int id, List<Response> responses) {
        return quizInterface.getScore(responses);
    }
}
