package com.klef.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Admin;
import com.klef.sdp.model.User;
import com.klef.sdp.model.EventManager;
import com.klef.sdp.model.TheatreOwner;
import com.klef.sdp.model.Event;
import com.klef.sdp.model.Movie;
import com.klef.sdp.repository.AdminRepository;
import com.klef.sdp.repository.UserRepository;
import com.klef.sdp.repository.EventManagerRepository;
import com.klef.sdp.repository.TheatreOwnerRepository;
import com.klef.sdp.repository.EventRepository;
import com.klef.sdp.repository.MovieRepository;
import com.klef.sdp.repository.TicketRepository;
import com.klef.sdp.repository.PaymentRepository;

@Service
public class AdminServiceImpl implements AdminService
{
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventManagerRepository eventManagerRepository;
    
    @Autowired
    private TheatreOwnerRepository theatreOwnerRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Override
    public Admin checkadminlogin(String username, String password) 
    {
        return adminRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public List<User> viewallusers() 
    {
        return userRepository.findAll();
    }
    
    @Override
    public String deleteuser(int userId) 
    {
        Optional<User> user = userRepository.findById(userId);
        
        if (user.isPresent()) 
        {    
            userRepository.deleteById(userId);
            return "User Deleted Successfully";
        } 
        else 
        {
            return "User ID Not Found";
        }
    }

    @Override
    public String addeventmanager(EventManager manager) 
    {
        eventManagerRepository.save(manager);
        return "Event Manager Added Successfully";
    }

    @Override
    public List<EventManager> viewalleventmanagers() 
    {
        return eventManagerRepository.findAll();
    }
    
    @Override
    public String deleteeventmanager(int managerId) 
    {
        Optional<EventManager> manager = eventManagerRepository.findById(managerId);
        
        if (manager.isPresent()) 
        {    
            eventManagerRepository.deleteById(managerId);
            return "Event Manager Deleted Successfully";
        } 
        else 
        {
            return "Event Manager ID Not Found";
        }
    }
    
    @Override
    public List<TheatreOwner> viewalltheatreowners() 
    {
        return theatreOwnerRepository.findAll();
    }
    
    @Override
    public String verifyTheatreOwner(int ownerId, boolean status) 
    {
        int rowsUpdated = theatreOwnerRepository.updateVerificationStatus(status, ownerId);
        
        if (rowsUpdated > 0) 
        {
            return "Theatre Owner Verification Status Updated Successfully";
        } 
        else 
        {
            return "Theatre Owner ID Not Found";
        }
    }
    
    @Override
    public String deletetheatreowner(int ownerId) 
    {
        Optional<TheatreOwner> owner = theatreOwnerRepository.findById(ownerId);
        
        if (owner.isPresent()) 
        {    
            theatreOwnerRepository.deleteById(ownerId);
            return "Theatre Owner Deleted Successfully";
        } 
        else 
        {
            return "Theatre Owner ID Not Found";
        }
    }
    
    @Override
    public List<Event> viewallevents() 
    {
        return eventRepository.findAll();
    }
    
    @Override
    public String approveEvent(int eventId, boolean status) 
    {
        int rowsUpdated = eventRepository.updateApprovalStatus(status, eventId);
        
        if (rowsUpdated > 0) 
        {
            return "Event Approval Status Updated Successfully";
        } 
        else 
        {
            return "Event ID Not Found";
        }
    }
    
    @Override
    public List<Movie> viewallmovies() 
    {
        return movieRepository.findAll();
    }
    
    @Override
    public String deletemovie(int movieId) 
    {
        Optional<Movie> movie = movieRepository.findById(movieId);
        
        if (movie.isPresent()) 
        {    
            movieRepository.deleteById(movieId);
            return "Movie Deleted Successfully";
        } 
        else 
        {
            return "Movie ID Not Found";
        }
    }
    
    @Override
    public long getUserCount() 
    {
        return userRepository.count();
    }

    @Override
    public long getEventManagerCount() 
    {
        return eventManagerRepository.count();
    }
    
    @Override
    public long getTheatreOwnerCount() 
    {
        return theatreOwnerRepository.count();
    }

    @Override
    public long getEventCount() 
    {
        return eventRepository.count();
    }
    
    @Override
    public long getMovieCount() 
    {
        return movieRepository.count();
    }
    
    @Override
    public long getTicketCount() 
    {
        return ticketRepository.count();
    }
    
    @Override
    public double getTotalRevenue() 
    {
        return paymentRepository.getTotalRevenue();
    }
}