package com.klef.sdp.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.User;
import com.klef.sdp.model.Event;
import com.klef.sdp.model.Movie;
import com.klef.sdp.model.Review;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.repository.UserRepository;
import com.klef.sdp.repository.EventRepository;
import com.klef.sdp.repository.MovieRepository;
import com.klef.sdp.repository.ReviewRepository;
import com.klef.sdp.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public String registerUser(User user)
    {
        // set default balance if not set
        if (user.getBalance() == 0.0) {
            user.setBalance(500.0);
        }
        userRepository.save(user);
        return "User Registered Successfully";
    }

    @Override
    public User checkuserlogin(String username, String password)
    {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public String updateUserProfile(User user)
    {
        Optional<User> optionalUser = userRepository.findById(user.getId());

        if(optionalUser.isPresent())
        {
            User existingUser = optionalUser.get();

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setMobileno(user.getMobileno());
            existingUser.setPassword(user.getPassword());
            existingUser.setLocation(user.getLocation());

            userRepository.save(existingUser);

            return "User Profile Updated Successfully";
        }
        else
        {
            return "User ID Not Found to Update";
        }
    }

    @Override
    public List<Event> viewAllEvents()
    {
        return eventRepository.findByApproved(true);
    }

    @Override
    public Event viewEventById(int eventId)
    {
        return eventRepository.findById(eventId).orElse(null);
    }

    @Override
    public List<Movie> viewAllMovies()
    {
        return movieRepository.findByActive(true);
    }

    @Override
    public Movie viewMovieById(int movieId)
    {
        return movieRepository.findById(movieId).orElse(null);
    }

    @Override
    public List<Event> searchEvents(String keyword)
    {
        return eventRepository.searchEventsByTitle(keyword);
    }

    @Override
    public List<Movie> searchMovies(String keyword)
    {
        return movieRepository.searchMoviesByTitle(keyword);
    }

    
    @Override
    @Transactional
    public String bookTicket(Ticket ticket)
    {
        
        User user = userRepository.findById(ticket.getUser().getId()).orElse(null);
        Event event = eventRepository.findById(ticket.getEvent().getId()).orElse(null);
        if (user == null || event == null) {
            return "Invalid User or Event";
        }

        // Parse requested seats
        List<String> requestedSeats = Arrays.stream(ticket.getSeatDetails().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        // Check for seat duplication or overbooking
        List<Ticket> bookedTickets = ticketRepository.findByEvent(event);
        Set<String> alreadyBookedSeats = bookedTickets.stream()
            .flatMap(t -> Arrays.stream(t.getSeatDetails().split(",")))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toSet());

        for (String seat : requestedSeats) {
            if (alreadyBookedSeats.contains(seat)) {
                return "Seat " + seat + " is already booked!";
            }
        }

        if (requestedSeats.size() != ticket.getNumberOfSeats()) {
            return "Selected seat count mismatch with numberOfSeats.";
        }

        if (requestedSeats.size() > event.getAvailableSeats()) {
            return "Not enough seats available.";
        }

        
        double totalCost = requestedSeats.size() * event.getCost();

        
        if (user.getBalance() < totalCost) {
            return "Insufficient Wallet Balance!";
        }
        user.setBalance(user.getBalance() - totalCost);
        userRepository.save(user);

        
        event.setAvailableSeats(event.getAvailableSeats() - requestedSeats.size());
        eventRepository.save(event);

       
        ticket.setPaymentDone(true);
        ticket.setTotalAmount(totalCost);

       
        ticketRepository.save(ticket);

        return "Ticket Booked Successfully";
    }

    @Override
    public List<Ticket> getUserTickets(int userId)
    {
        User user = userRepository.findById(userId).orElse(null);
        return ticketRepository.findByUser(user);
    }

    @Override
    public String addReview(Review review)
    {
        reviewRepository.save(review);
        return "Review Added Successfully";
    }

    @Override
    public List<Review> getEventReviews(int eventId)
    {
        Event event = eventRepository.findById(eventId).orElse(null);
        return reviewRepository.findByEvent(event);
    }

    @Override
    public double getUserWalletBalance(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getBalance() : 0.0;
    }

    @Override
    @Transactional
    public String addToUserWallet(int userId, double amount) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "User not found";
        if (amount <= 0) return "Amount must be positive";
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        return "Amount added successfully. New balance: " + user.getBalance();
    }
}