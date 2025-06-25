package com.example.questionService.controller;

import com.example.questionService.model.Question;
import com.example.questionService.model.QuestionDto;
import com.example.questionService.model.Response;
import com.example.questionService.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final Environment environment;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getAllQuestionsPerCategory(@PathVariable String category) {
        return questionService.getAllQuestionsPerCategory(category);
    }

    @GetMapping("difficultyLevel/{difficultyLevel}")
    public ResponseEntity<List<Question>> getAllQuestionsByDifficulty(@PathVariable String difficultyLevel) {
        return questionService.getAllQuestionsByDifficulty(difficultyLevel);
    }

    @PostMapping
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("create")
    public ResponseEntity<List<Integer>> createQuestionsForUser(@RequestParam String category,
                                                                 @RequestParam int numQuestions){
        return questionService.createQuestionsForUser(category,numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionDto>>getQuestionsByID(@RequestBody List<Integer> questionIds){
        /// to see which server is used that is called Load Balancing

        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsByID(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);

    }

}
