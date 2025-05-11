package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.model.Admin;
import com.klef.sdp.model.User;
import com.klef.sdp.model.EventManager;
import com.klef.sdp.model.TheatreOwner;
import com.klef.sdp.model.Event;
import com.klef.sdp.model.Movie;

public interface AdminService 
{
  public Admin checkadminlogin(String username, String password);
  
  public List<User> viewallusers();
  public String deleteuser(int userId);
  
  public String addeventmanager(EventManager manager);
  public List<EventManager> viewalleventmanagers();
  public String deleteeventmanager(int managerId);
  
  public List<TheatreOwner> viewalltheatreowners();
  public String verifyTheatreOwner(int ownerId, boolean status);
  public String deletetheatreowner(int ownerId);
  
  public List<Event> viewallevents();
  public String approveEvent(int eventId, boolean status);
  
  public List<Movie> viewallmovies();
  public String deletemovie(int movieId);
  
  public long getUserCount();
  public long getEventManagerCount();
  public long getTheatreOwnerCount();
  public long getEventCount();
  public long getMovieCount();
  public long getTicketCount();
  public double getTotalRevenue();
}