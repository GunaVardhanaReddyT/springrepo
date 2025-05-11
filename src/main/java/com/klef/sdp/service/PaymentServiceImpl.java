package com.klef.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Payment;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.repository.PaymentRepository;
import com.klef.sdp.repository.TicketRepository;

@Service
public class PaymentServiceImpl implements PaymentService
{
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public String processPayment(Payment payment) 
	{
		paymentRepository.save(payment);
		
		// Update ticket payment status if payment is successful
		if ("COMPLETED".equals(payment.getStatus())) {
			Ticket ticket = payment.getTicket();
			ticketRepository.updatePaymentStatus(true, ticket.getId());
			ticketRepository.updateStatusById("CONFIRMED", ticket.getId());
		}
		
		return "Payment Processed Successfully";
	}

	@Override
	public Payment getPaymentById(int paymentId) 
	{
		return paymentRepository.findById(paymentId).orElse(null);
	}

	@Override
	public Payment getPaymentByReference(String reference) 
	{
		return paymentRepository.findByPaymentReference(reference);
	}

	@Override
	public Payment getPaymentByTicketId(int ticketId) 
	{
		Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
		return paymentRepository.findByTicket(ticket);
	}

	@Override
	public List<Payment> getPaymentsByStatus(String status) 
	{
		return paymentRepository.findByStatus(status);
	}

	@Override
	public String updatePaymentStatus(int paymentId, String status) 
	{
		int rowsUpdated = paymentRepository.updatePaymentStatus(status, paymentId);
		
		if (rowsUpdated > 0) 
		{
			// If payment is now completed, update ticket status too
			if ("COMPLETED".equals(status)) {
				Payment payment = paymentRepository.findById(paymentId).orElse(null);
				if (payment != null) {
					ticketRepository.updatePaymentStatus(true, payment.getTicket().getId());
					ticketRepository.updateStatusById("CONFIRMED", payment.getTicket().getId());
				}
			}
			
			return "Payment Status Updated Successfully";
		} 
		else 
		{
			return "Payment ID Not Found";
		}
	}
}