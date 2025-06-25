package com.example.QuizService.controller;

import com.example.QuizService.model.QuestionDto;
import com.example.QuizService.model.Response;
import com.example.QuizService.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("quiz")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                           @RequestParam int numQ,
                                           @RequestParam String title){
       return quizService.createQuiz(category,numQ,title);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionDto>> getQuiz(@PathVariable int id){
        return quizService.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,
                                              @RequestBody List<Response> responses){
        return quizService.submitQuiz(id,responses);
    }
}
