package com.klef.sdp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Event;
import com.klef.sdp.model.EventManager;
import com.klef.sdp.model.Review;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.repository.EventManagerRepository;
import com.klef.sdp.repository.EventRepository;
import com.klef.sdp.repository.ReviewRepository;
import com.klef.sdp.repository.TicketRepository;

@Service
public class EventManagerServiceImpl implements EventManagerService
{
	@Autowired
    private EventManagerRepository eventManagerRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public String registerEventManager(EventManager manager) 
	{
		eventManagerRepository.save(manager);
		return "Event Manager Registered Successfully";
	}

	@Override
	public EventManager checklogin(String username, String password) 
	{
		return eventManagerRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	public String updateProfile(EventManager manager) 
	{
		Optional<EventManager> optionalManager = eventManagerRepository.findById(manager.getId());
		
		if(optionalManager.isPresent())
		{
			EventManager existingManager = optionalManager.get();
			
			existingManager.setName(manager.getName());
			existingManager.setEmail(manager.getEmail());
			existingManager.setMobileno(manager.getMobileno());
			existingManager.setPassword(manager.getPassword());
			existingManager.setCompany(manager.getCompany());
			existingManager.setAddress(manager.getAddress());
			
			eventManagerRepository.save(existingManager);
			
			return "Profile Updated Successfully";
		}
		else
		{
			return "Manager ID Not Found to Update";
		}
	}

	@Override
	public String addEvent(Event event) 
	{
		eventRepository.save(event);
		return "Event Added Successfully";
	}

	@Override
	public List<Event> getEventsByManager(int managerId) 
	{
		EventManager manager = eventManagerRepository.findById(managerId).orElse(null);
		return eventRepository.findByManager(manager);
	}

	@Override
	public String updateEvent(Event event) 
	{
		Optional<Event> optionalEvent = eventRepository.findById(event.getId());
		
		if(optionalEvent.isPresent())
		{
			Event existingEvent = optionalEvent.get();
			
			// Update fields but preserve approved status and manager
			event.setApproved(existingEvent.isApproved());
			event.setManager(existingEvent.getManager());
			
			eventRepository.save(event);
			
			return "Event Updated Successfully";
		}
		else
		{
			return "Event ID Not Found to Update";
		}
	}

	@Override
	public String deleteEvent(int eventId) 
	{
		Optional<Event> event = eventRepository.findById(eventId);
	    
	    if (event.isPresent()) 
	    {	
	        eventRepository.deleteById(eventId);
	        return "Event Deleted Successfully";
	    } 
	    else 
	    {
	        return "Event ID Not Found";
	    }
	}

	@Override
	public List<Ticket> getTicketsByManager(int managerId) 
	{
		return ticketRepository.getTicketsByManager(managerId);
	}

	@Override
	public List<Review> getReviewsByManager(int managerId) 
	{
		return reviewRepository.getReviewsByManager(managerId);
	}
	
	@Override
	public Map<String, Object> getEventStatistics(int eventId) 
	{
		Map<String, Object> statistics = new HashMap<>();
		
		Event event = eventRepository.findById(eventId).orElse(null);
		
		if(event != null) {
			// Get tickets for this event
			List<Ticket> tickets = ticketRepository.findByEvent(event);
			
			// Count total tickets, confirmed tickets, etc.
			int totalTickets = tickets.size();
			long confirmedTickets = tickets.stream()
					.filter(t -> "CONFIRMED".equals(t.getStatus()))
					.count();
			long pendingTickets = tickets.stream()
					.filter(t -> "PENDING".equals(t.getStatus()))
					.count();
			long cancelledTickets = tickets.stream()
					.filter(t -> "CANCELLED".equals(t.getStatus()))
					.count();
			
			// Calculate total seats sold
			int seatsSold = tickets.stream()
					.filter(t -> !"CANCELLED".equals(t.getStatus()))
					.mapToInt(Ticket::getNumberOfSeats)
					.sum();
			
			// Calculate total revenue
			double totalRevenue = tickets.stream()
					.filter(t -> t.isPaymentDone())
					.mapToDouble(Ticket::getTotalAmount)
					.sum();
			
			// Get average rating
			Double avgRating = reviewRepository.getAverageRatingForEvent(eventId);
			
			// Add all data to statistics map
			statistics.put("totalTickets", totalTickets);
			statistics.put("confirmedTickets", confirmedTickets);
			statistics.put("pendingTickets", pendingTickets);
			statistics.put("cancelledTickets", cancelledTickets);
			statistics.put("seatsSold", seatsSold);
			statistics.put("totalSeats", event.getCapacity());
			statistics.put("availableSeats", event.getAvailableSeats());
			statistics.put("occupancyRate", (event.getCapacity() > 0) ? 
					((double)seatsSold / event.getCapacity()) * 100 : 0);
			statistics.put("totalRevenue", totalRevenue);
			statistics.put("averageRating", avgRating != null ? avgRating : 0);
		}
		
		return statistics;
	}
}