package com.klef.sdp.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.model.Payment;
import com.klef.sdp.service.PaymentService;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController 
{
   @Autowired
   private PaymentService paymentService;
   
   @PostMapping("/processpayment")
   public ResponseEntity<String> processPayment(@RequestBody Payment payment)
   {
       try
       {
           // Generate payment reference
           String paymentRef = "PAY" + (new Random().nextInt(900000) + 100000);
           payment.setPaymentReference(paymentRef);
           
           String result = paymentService.processPayment(payment);
           return ResponseEntity.ok(result);
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Payment processing failed: " + e.getMessage());
       }
   }
   
   @GetMapping("/{id}")
   public ResponseEntity<?> getPaymentById(@PathVariable int id)
   {
       try
       {
           Payment payment = paymentService.getPaymentById(id);
           if (payment != null)
           {
               return ResponseEntity.ok(payment);
           }
           else
           {
               return ResponseEntity.status(404).body("Payment not found");
           }
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Error retrieving payment: " + e.getMessage());
       }
   }
   
   @GetMapping("/reference/{reference}")
   public ResponseEntity<?> getPaymentByReference(@PathVariable String reference)
   {
       try
       {
           Payment payment = paymentService.getPaymentByReference(reference);
           if (payment != null)
           {
               return ResponseEntity.ok(payment);
           }
           else
           {
               return ResponseEntity.status(404).body("Payment not found");
           }
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Error retrieving payment: " + e.getMessage());
       }
   }
   
   @GetMapping("/ticket/{ticketId}")
   public ResponseEntity<?> getPaymentByTicket(@PathVariable int ticketId)
   {
       try
       {
           Payment payment = paymentService.getPaymentByTicketId(ticketId);
           if (payment != null)
           {
               return ResponseEntity.ok(payment);
           }
           else
           {
               return ResponseEntity.status(404).body("Payment not found for this ticket");
           }
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Error retrieving payment: " + e.getMessage());
       }
   }
   
   @GetMapping("/status/{status}")
   public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status)
   {
       List<Payment> payments = paymentService.getPaymentsByStatus(status);
       return ResponseEntity.ok(payments);
   }
   
   @PutMapping("/updatestatus/{id}/{status}")
   public ResponseEntity<String> updatePaymentStatus(@PathVariable int id, @PathVariable String status)
   {
       try
       {
           String result = paymentService.updatePaymentStatus(id, status);
           return ResponseEntity.ok(result);
       }
       catch (Exception e)
       {
           return ResponseEntity.status(500).body("Failed to update payment status: " + e.getMessage());
       }
   }
}