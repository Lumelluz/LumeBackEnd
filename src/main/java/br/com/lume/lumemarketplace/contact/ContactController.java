package br.com.lume.lumemarketplace.contact;

import br.com.lume.lumemarketplace.ticket.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> submitContactForm(@Valid @RequestBody ContactFormDTO dto) {
        try {
            ticketService.createTicket(dto);
            return ResponseEntity.ok("Mensagem enviada com sucesso! Entraremos em contato em breve.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao enviar mensagem.");
        }
    }
}