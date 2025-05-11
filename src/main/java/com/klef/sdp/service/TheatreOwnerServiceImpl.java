package com.klef.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Movie;
import com.klef.sdp.model.TheatreOwner;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.repository.MovieRepository;
import com.klef.sdp.repository.TheatreOwnerRepository;
import com.klef.sdp.repository.TicketRepository;

@Service
public class TheatreOwnerServiceImpl implements TheatreOwnerService
{
    @Autowired
    private TheatreOwnerRepository theatreOwnerRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public String registerTheatreOwner(TheatreOwner owner) 
    {
        theatreOwnerRepository.save(owner);
        return "Theatre Owner Registered Successfully";
    }

    @Override
    public TheatreOwner checklogin(String username, String password) 
    {
        return theatreOwnerRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public String updateProfile(TheatreOwner owner) 
    {
        Optional<TheatreOwner> optionalOwner = theatreOwnerRepository.findById(owner.getId());
        
        if(optionalOwner.isPresent())
        {
            TheatreOwner existingOwner = optionalOwner.get();
            
            existingOwner.setName(owner.getName());
            existingOwner.setEmail(owner.getEmail());
            existingOwner.setMobileno(owner.getMobileno());
            existingOwner.setPassword(owner.getPassword());
            existingOwner.setCompany(owner.getCompany());
            
            // Preserve verification status
            existingOwner.setVerified(optionalOwner.get().isVerified());
            
            theatreOwnerRepository.save(existingOwner);
            
            return "Profile Updated Successfully";
        }
        else
        {
            return "Theatre Owner ID Not Found to Update";
        }
    }

    @Override
    public String addMovie(Movie movie) 
    {
        movieRepository.save(movie);
        return "Movie Added Successfully";
    }

    @Override
    public List<Movie> getAllMovies() 
    {
        return movieRepository.findAll();
    }

    @Override
    public String updateMovie(Movie movie) 
    {
        Optional<Movie> optionalMovie = movieRepository.findById(movie.getId());
        
        if(optionalMovie.isPresent())
        {
            movieRepository.save(movie);
            return "Movie Updated Successfully";
        }
        else
        {
            return "Movie ID Not Found to Update";
        }
    }

    @Override
    public String deleteMovie(int movieId) 
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
    public List<Ticket> getTicketsByTheatreOwner(int ownerId) 
    {
        return ticketRepository.getTicketsByTheatreOwner(ownerId);
    }
}