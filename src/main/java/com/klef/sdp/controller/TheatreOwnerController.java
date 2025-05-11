package com.klef.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.model.Movie;
import com.klef.sdp.model.TheatreOwner;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.service.TheatreOwnerService;

@RestController
@RequestMapping("/theatreowner")
@CrossOrigin("*")
public class TheatreOwnerController 
{
    @Autowired
    private TheatreOwnerService theatreOwnerService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody TheatreOwner owner)
    {
        try
        {
            owner.setVerified(false);
            String output = theatreOwnerService.registerTheatreOwner(owner);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Registration Failed ...");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody TheatreOwner owner) 
    {
        try 
        {
            TheatreOwner o = theatreOwnerService.checklogin(owner.getUsername(), owner.getPassword());

            if (o != null) 
            {
                if(o.isVerified())
                {
                    return ResponseEntity.ok(o);
                }
                else
                {
                    return ResponseEntity.status(403).body("Account not verified");
                }
            } 
            else 
            {
                return ResponseEntity.status(401).body("Invalid Username or Password");
            }
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }
    
    @PutMapping("/updateprofile")
    public ResponseEntity<String> updateprofile(@RequestBody TheatreOwner owner)
    {
        try
        {
            String output = theatreOwnerService.updateProfile(owner);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Update Profile ... !!");
        }
    }
    
    @PostMapping("/addmovie")
    public ResponseEntity<String> addmovie(@RequestBody Movie movie)
    {
        try
        {
            movie.setActive(true);
            String output = theatreOwnerService.addMovie(movie);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Add Movie ... !!");
        }
    }
    
    @GetMapping("/viewallmovies")
    public ResponseEntity<List<Movie>> viewallmovies()
    {
        List<Movie> movies = theatreOwnerService.getAllMovies();
        return ResponseEntity.ok(movies);
    }
    
    @PutMapping("/updatemovie")
    public ResponseEntity<String> updatemovie(@RequestBody Movie movie)
    {
        try
        {
            String output = theatreOwnerService.updateMovie(movie);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Update Movie ... !!");
        }
    }
    
    @DeleteMapping("/deletemovie")
    public ResponseEntity<String> deletemovie(@RequestParam int movieId)
    {
        try
        {
            String output = theatreOwnerService.deleteMovie(movieId);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Delete Movie ... !!");
        }
    }
    
    @GetMapping("/viewalltickets/{ownerId}")
    public ResponseEntity<List<Ticket>> viewalltickets(@PathVariable int ownerId)
    {
        List<Ticket> tickets = theatreOwnerService.getTicketsByTheatreOwner(ownerId);
        return ResponseEntity.ok(tickets);
    }
}