package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.model.User;
import com.klef.sdp.model.Event;
import com.klef.sdp.model.Movie;
import com.klef.sdp.model.Review;
import com.klef.sdp.model.Ticket;

public interface UserService 
{
  public String registerUser(User user);
  public User checkuserlogin(String username, String password);
  public String updateUserProfile(User user);
  
  public List<Event> viewAllEvents();
  public Event viewEventById(int eventId);
  
  public List<Movie> viewAllMovies();
  public Movie viewMovieById(int movieId);
  
  public List<Event> searchEvents(String keyword);
  public List<Movie> searchMovies(String keyword);
  
  public String bookTicket(Ticket ticket);
  public List<Ticket> getUserTickets(int userId);
  
  public String addReview(Review review);
  public List<Review> getEventReviews(int eventId);

  // Optional: Wallet-related methods (for wallet UI)
  public double getUserWalletBalance(int userId);
  public String addToUserWallet(int userId, double amount);
}