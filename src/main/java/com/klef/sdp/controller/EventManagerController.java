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

import com.klef.sdp.model.Event;
import com.klef.sdp.model.EventManager;
import com.klef.sdp.model.Review;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.service.EventManagerService;

@RestController
@RequestMapping("/eventmanager")
@CrossOrigin("*")
public class EventManagerController 
{
    @Autowired
    private EventManagerService eventManagerService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody EventManager manager)
    {
        try
        {
            String output = eventManagerService.registerEventManager(manager);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Registration Failed ...");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EventManager manager) 
    {
        try 
        {
            EventManager m = eventManagerService.checklogin(manager.getUsername(), manager.getPassword());

            if (m != null) 
            {
                return ResponseEntity.ok(m);
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
    public ResponseEntity<String> updateprofile(@RequestBody EventManager manager)
    {
        try
        {
            String output = eventManagerService.updateProfile(manager);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Update Profile ... !!");
        }
    }
    
    @PostMapping("/addevent")
    public ResponseEntity<String> addevent(@RequestBody Event event)
    {
        try
        {
            event.setApproved(false);
            event.setAvailableSeats(event.getCapacity());
            String output = eventManagerService.addEvent(event);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Add Event ... !!");
        }
    }
    
    @GetMapping("/viewmyevents/{managerId}")
    public ResponseEntity<List<Event>> viewmyevents(@PathVariable int managerId)
    {
        List<Event> events = eventManagerService.getEventsByManager(managerId);
        return ResponseEntity.ok(events);
    }
    
    @PutMapping("/updateevent")
    public ResponseEntity<String> updateevent(@RequestBody Event event)
    {
        try
        {
            String output = eventManagerService.updateEvent(event);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Update Event ... !!");
        }
    }
    
    @DeleteMapping("/deleteevent")
    public ResponseEntity<String> deleteevent(@RequestParam int eventId)
    {
        try
        {
            String output = eventManagerService.deleteEvent(eventId);
            return ResponseEntity.ok(output);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Delete Event ... !!");
        }
    }
    
    @GetMapping("/viewalltickets/{managerId}")
    public ResponseEntity<List<Ticket>> viewalltickets(@PathVariable int managerId)
    {
        List<Ticket> tickets = eventManagerService.getTicketsByManager(managerId);
        return ResponseEntity.ok(tickets);
    }
    
    @GetMapping("/viewreviews/{managerId}")
    public ResponseEntity<List<Review>> viewreviews(@PathVariable int managerId)
    {
        List<Review> reviews = eventManagerService.getReviewsByManager(managerId);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/eventstatistics/{eventId}")
    public ResponseEntity<?> getEventStatistics(@PathVariable int eventId)
    {
        try
        {
            return ResponseEntity.ok(eventManagerService.getEventStatistics(eventId));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("Failed to Get Event Statistics ... !!");
        }
    }
}