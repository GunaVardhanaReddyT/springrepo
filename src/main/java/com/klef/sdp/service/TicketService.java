package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.model.Ticket;

public interface TicketService 
{
  public Ticket getTicketById(int ticketId);
  public Ticket getTicketByTicketNumber(String ticketNumber);
  public Ticket getTicketByBookingReference(String bookingReference);
  public List<Ticket> getTicketsByUser(int userId);
  public List<Ticket> getTicketsByEvent(int eventId);
  public List<Ticket> getTicketsByStatus(String status);
  public String updateTicketStatus(int ticketId, String status);
}