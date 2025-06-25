package com.example.questionService.service;

import com.example.questionService.model.Question;
import com.example.questionService.model.QuestionDto;
import com.example.questionService.model.Response;
import com.example.questionService.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Question>> getAllQuestionsPerCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getAllQuestionsByDifficulty(String difficultyLevel) {
        try {
            return new ResponseEntity<>(questionRepository.findByDifficultyLevel(difficultyLevel), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionRepository.save(question);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> createQuestionsForUser(String category,
                                                                int numQuestions) {
        List<Integer> questions =
                questionRepository.findRandomQuestionsByCategory(category, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);

    }

    public ResponseEntity<List<QuestionDto>> getQuestionsByID(List<Integer> questionIds) {
        List<Question> questions = questionRepository.findAllById(questionIds);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setId(question.getId());
            questionDto.setQuestionTitle(question.getQuestionTitle());
            questionDto.setOption1(question.getOption1());
            questionDto.setOption2(question.getOption2());
            questionDto.setOption3(question.getOption3());
            questionDto.setOption4(question.getOption4());
            questionDtoList.add(questionDto);
        }
        return new ResponseEntity<>(questionDtoList, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int rightAnswers = 0;
        for (int i = 0; i < responses.size(); i++) {
            Question question = questionRepository.findById(responses.get(i).getId()).orElseThrow(
                    () -> new RuntimeException("Question not found")
            );
            if (responses.get(i).getAnswer().equals(question.getRightAnswer())) {
                rightAnswers++;
            }
        }
        return new ResponseEntity<>(rightAnswers, HttpStatus.OK);
    }
}
