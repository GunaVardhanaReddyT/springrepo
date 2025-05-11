package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.model.Movie;
import com.klef.sdp.model.TheatreOwner;
import com.klef.sdp.model.Ticket;

public interface TheatreOwnerService 
{
    public String registerTheatreOwner(TheatreOwner owner);
    public TheatreOwner checklogin(String username, String password);
    public String updateProfile(TheatreOwner owner);
    
    public String addMovie(Movie movie);
    public List<Movie> getAllMovies();
    public String updateMovie(Movie movie);
    public String deleteMovie(int movieId);
    
    public List<Ticket> getTicketsByTheatreOwner(int ownerId);
}