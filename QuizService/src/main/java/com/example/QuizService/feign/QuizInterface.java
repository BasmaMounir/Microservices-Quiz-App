package com.example.QuizService.feign;

import com.example.QuizService.model.QuestionDto;
import com.example.QuizService.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("QUESTIONSERVICE")
public interface QuizInterface {

    @GetMapping("question/create")
    public ResponseEntity<List<Integer>> createQuestionsForUser(@RequestParam String category,
                                                                @RequestParam int numQuestions);
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionDto>>getQuestionsByID(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
