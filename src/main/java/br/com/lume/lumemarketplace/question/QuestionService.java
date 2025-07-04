package br.com.lume.lumemarketplace.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lume.lumemarketplace.entity.Product;
import br.com.lume.lumemarketplace.entity.ProductRepository;
import br.com.lume.lumemarketplace.entity.Question;
import br.com.lume.lumemarketplace.entity.QuestionRepository;
import br.com.lume.lumemarketplace.entity.User;
import br.com.lume.lumemarketplace.entity.UserRepository;
import jakarta.transaction.Transactional;

record AskQuestionDTO(String questionText) {}

record QuestionAnswerDTO(Long id, String questionText, String answerText, String userName, LocalDateTime createdAt) {
    public static QuestionAnswerDTO fromEntity(Question q) {
        return new QuestionAnswerDTO(
            q.getId(),
            q.getQuestionText(),
            q.getAnswerText(),
            q.getUser().getNomeCompleto(),
            q.getCreatedAt()
        );
    }
}

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<QuestionAnswerDTO> getQuestionsForProduct(Long productId) {
        return questionRepository.findByProductIdOrderByCreatedAtDesc(productId).stream()
                .map(QuestionAnswerDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void postQuestion(Long productId, AskQuestionDTO dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Utilizador não encontrado."));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        Question question = new Question();
        question.setUser(user);
        question.setProduct(product);
        question.setQuestionText(dto.questionText());
        question.setCreatedAt(LocalDateTime.now());
        
        questionRepository.save(question);
    }
    
    @Transactional
    public void answerQuestion(Long questionId, AnswerDTO dto, String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Utilizador não encontrado."));
            
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new RuntimeException("Pergunta não encontrada."));

        if (!question.getProduct().getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("Apenas o vendedor pode responder a perguntas sobre este produto.");
        }

        question.setAnswerText(dto.answerText());
        question.setAnsweredAt(LocalDateTime.now());
        questionRepository.save(question);
    }
}