package br.com.lume.lumemarketplace.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/products/{productId}/questions")
    public ResponseEntity<List<QuestionAnswerDTO>> getQuestionsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(questionService.getQuestionsForProduct(productId));
    }

    @PostMapping("/products/{productId}/questions")
    public ResponseEntity<?> askQuestion(@PathVariable Long productId, @Valid @RequestBody AskQuestionDTO dto, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            questionService.postQuestion(productId, dto, userEmail);
            return ResponseEntity.ok("Pergunta enviada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/questions/{questionId}/answer")
    public ResponseEntity<?> answerQuestion(@PathVariable Long questionId, @Valid @RequestBody AnswerDTO dto, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            questionService.answerQuestion(questionId, dto, userEmail);
            return ResponseEntity.ok("Resposta enviada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
