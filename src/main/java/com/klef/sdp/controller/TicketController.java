package com.klef.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.model.Ticket;
import com.klef.sdp.service.TicketService;

@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController 
{
   @Autowired
   private TicketService ticketService;
   
   @GetMapping("/{id}")
   public ResponseEntity<?> getTicketById(@PathVariable int id)
   {
       try
       {
           Ticket ticket = ticketService.getTicketById(id);
           if (ticket != null)
           {
               return ResponseEntity.ok(ticket);
           }
           else
           {
               return ResponseEntity.status(404).body("Ticket not found");
           }
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Error retrieving ticket: " + e.getMessage());
       }
   }
   
   @GetMapping("/reference/{reference}")
   public ResponseEntity<?> getTicketByReference(@PathVariable String reference)
   {
       try
       {
           Ticket ticket = ticketService.getTicketByBookingReference(reference);
           if (ticket != null)
           {
               return ResponseEntity.ok(ticket);
           }
           else
           {
               return ResponseEntity.status(404).body("Ticket not found");
           }
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Error retrieving ticket: " + e.getMessage());
       }
   }
   
   @GetMapping("/ticketnumber/{ticketNumber}")
   public ResponseEntity<?> getTicketByTicketNumber(@PathVariable String ticketNumber)
   {
       try
       {
           Ticket ticket = ticketService.getTicketByTicketNumber(ticketNumber);
           if (ticket != null)
           {
               return ResponseEntity.ok(ticket);
           }
           else
           {
               return ResponseEntity.status(404).body("Ticket not found");
           }
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Error retrieving ticket: " + e.getMessage());
       }
   }
   
   @GetMapping("/user/{userId}")
   public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable int userId)
   {
       List<Ticket> tickets = ticketService.getTicketsByUser(userId);
       return ResponseEntity.ok(tickets);
   }
   
   @GetMapping("/event/{eventId}")
   public ResponseEntity<List<Ticket>> getTicketsByEvent(@PathVariable int eventId)
   {
       List<Ticket> tickets = ticketService.getTicketsByEvent(eventId);
       return ResponseEntity.ok(tickets);
   }
   
   @GetMapping("/status/{status}")
   public ResponseEntity<List<Ticket>> getTicketsByStatus(@PathVariable String status)
   {
       List<Ticket> tickets = ticketService.getTicketsByStatus(status);
       return ResponseEntity.ok(tickets);
   }
   
   @PutMapping("/updatestatus")
   public ResponseEntity<String> updateTicketStatus(@RequestParam int ticketId, @RequestParam String status)
   {
       try
       {
           String result = ticketService.updateTicketStatus(ticketId, status);
           return ResponseEntity.ok(result);
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Failed to update ticket status: " + e.getMessage());
       }
   }
}