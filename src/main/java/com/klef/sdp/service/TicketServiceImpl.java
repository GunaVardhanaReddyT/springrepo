package com.klef.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Event;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.model.User;
import com.klef.sdp.repository.EventRepository;
import com.klef.sdp.repository.TicketRepository;
import com.klef.sdp.repository.UserRepository;

@Service
public class TicketServiceImpl implements TicketService
{
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Ticket getTicketById(int ticketId) 
	{
		return ticketRepository.findById(ticketId).orElse(null);
	}

	@Override
	public Ticket getTicketByTicketNumber(String ticketNumber) 
	{
		return ticketRepository.findByTicketNumber(ticketNumber);
	}

	@Override
	public Ticket getTicketByBookingReference(String bookingReference) 
	{
		return ticketRepository.findByBookingReference(bookingReference);
	}

	@Override
	public List<Ticket> getTicketsByUser(int userId) 
	{
		User user = userRepository.findById(userId).orElse(null);
		return ticketRepository.findByUser(user);
	}

	@Override
	public List<Ticket> getTicketsByEvent(int eventId) 
	{
		Event event = eventRepository.findById(eventId).orElse(null);
		return ticketRepository.findByEvent(event);
	}

	@Override
	public List<Ticket> getTicketsByStatus(String status) 
	{
		return ticketRepository.findByStatus(status);
	}

	@Override
	public String updateTicketStatus(int ticketId, String status) 
	{
		int rowsUpdated = ticketRepository.updateStatusById(status, ticketId);
		
		if (rowsUpdated > 0) 
		{
			// If cancelling ticket, update available seats
			if ("CANCELLED".equals(status)) {
				Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
				if (ticketOpt.isPresent()) {
					Ticket ticket = ticketOpt.get();
					Event event = ticket.getEvent();
					event.setAvailableSeats(event.getAvailableSeats() + ticket.getNumberOfSeats());
					eventRepository.save(event);
				}
			}
			
			return "Ticket Status Updated Successfully";
		} 
		else 
		{
			return "Ticket ID Not Found";
		}
	}
}