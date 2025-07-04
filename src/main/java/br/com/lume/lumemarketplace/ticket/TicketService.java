package br.com.lume.lumemarketplace.ticket;

import br.com.lume.lumemarketplace.contact.ContactFormDTO;
import br.com.lume.lumemarketplace.entity.Ticket;
import br.com.lume.lumemarketplace.entity.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public void createTicket(ContactFormDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setName(dto.name());
        ticket.setEmail(dto.email());
        ticket.setSubject(dto.subject());
        ticket.setMessage(dto.message());
        ticket.setStatus("NOVO");
        ticket.setCreatedAt(LocalDateTime.now());
        
        ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}