package com.klef.sdp.service;

import java.util.List;
import java.util.Map;

import com.klef.sdp.model.Event;
import com.klef.sdp.model.EventManager;
import com.klef.sdp.model.Review;
import com.klef.sdp.model.Ticket;

public interface EventManagerService 
{
  public String registerEventManager(EventManager manager);
  public EventManager checklogin(String username, String password);
  public String updateProfile(EventManager manager);
  
  public String addEvent(Event event);
  public List<Event> getEventsByManager(int managerId);
  public String updateEvent(Event event);
  public String deleteEvent(int eventId);
  
  public List<Ticket> getTicketsByManager(int managerId);
  public List<Review> getReviewsByManager(int managerId);
  
  public Map<String, Object> getEventStatistics(int eventId);
}