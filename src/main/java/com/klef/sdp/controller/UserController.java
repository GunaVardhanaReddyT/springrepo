package com.klef.sdp.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.model.User;
import com.klef.sdp.model.Event;
import com.klef.sdp.model.Movie;
import com.klef.sdp.model.Review;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController 
{
   @Autowired
   private UserService userService;
	
   @GetMapping("/")
   public String home()
   {
	   return "Online Ticket Booking System";
   }
   
   @PostMapping("/register")
   public ResponseEntity<String> userregister(@RequestBody User user)
   {
	   try
	   {
		  String output = userService.registerUser(user);
		  return ResponseEntity.ok(output); // 200 - success
	   }
	   catch(Exception e)
	   {
		   return ResponseEntity.status(500).body("User Registration Failed ...");
	   }
   }
   
   @PostMapping("/login")
   public ResponseEntity<?> checkuserlogin(@RequestBody User user) 
   {
       try 
       {
           User u = userService.checkuserlogin(user.getUsername(), user.getPassword());

           if (u != null) 
           {
               return ResponseEntity.ok(u); // if login is successful
           } 
           else 
           {
               return ResponseEntity.status(401).body("Invalid Username or Password"); // if login is fail
           }
       } 
       catch (Exception e) 
       {
           return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
       }
   }
   
   @PutMapping("/updateprofile")
   public ResponseEntity<String> updateprofile(@RequestBody User user)
   {
 	  try
 	   {
 		  String output = userService.updateUserProfile(user);
 		  return ResponseEntity.ok(output);
 	   }
 	   catch(Exception e)
 	   {
 		    System.out.println(e.getMessage());
 		    return ResponseEntity.status(500).body("Failed to Update User Profile ... !!"); 
 	   }
   }

   @GetMapping("/viewallevents")
   public ResponseEntity<List<Event>> viewallevents()
   {
 	 List<Event> events = userService.viewAllEvents();
 	 
 	 return ResponseEntity.ok(events); // 200 - success
   }
   
   @GetMapping("/viewevent/{id}")
   public ResponseEntity<?> vieweventbyid(@PathVariable("id") int id)
   {
       try 
       {
           Event event = userService.viewEventById(id);
           if (event != null) 
           {
               return ResponseEntity.ok(event);
           } 
           else 
           {
               return ResponseEntity.status(404).body("Event not found");
           }
       } 
       catch (Exception e) 
       {
           return ResponseEntity.status(500).body("Error retrieving event: " + e.getMessage());
       }
   }
   
   @GetMapping("/viewallmovies")
   public ResponseEntity<List<Movie>> viewallmovies()
   {
 	 List<Movie> movies = userService.viewAllMovies();
 	 
 	 return ResponseEntity.ok(movies); // 200 - success
   }
   
   @GetMapping("/viewmovie/{id}")
   public ResponseEntity<?> viewmoviebyid(@PathVariable("id") int id)
   {
       try 
       {
           Movie movie = userService.viewMovieById(id);
           if (movie != null) 
           {
               return ResponseEntity.ok(movie);
           } 
           else 
           {
               return ResponseEntity.status(404).body("Movie not found");
           }
       } 
       catch (Exception e) 
       {
           return ResponseEntity.status(500).body("Error retrieving movie: " + e.getMessage());
       }
   }
   
   @GetMapping("/searchevents")
   public ResponseEntity<List<Event>> searchEvents(@RequestParam String keyword)
   {
       List<Event> events = userService.searchEvents(keyword);
       return ResponseEntity.ok(events);
   }
   
   @GetMapping("/searchmovies")
   public ResponseEntity<List<Movie>> searchMovies(@RequestParam String keyword)
   {
       List<Movie> movies = userService.searchMovies(keyword);
       return ResponseEntity.ok(movies);
   }
   
   @PostMapping("/bookticket")
   public ResponseEntity<String> bookTicket(@RequestBody Ticket ticket) 
   {
      try
      {
    	  // Generate a ticket number
    	  String ticketNumber = "TKT" + (new Random().nextInt(900000) + 100000);
    	  ticket.setTicketNumber(ticketNumber);
    	  
    	  // Generate a booking reference
    	  String bookingRef = "BK" + (new Random().nextInt(900000) + 100000);
    	  ticket.setBookingReference(bookingRef);
    	  
    	  // Set initial status
    	  ticket.setStatus("PENDING");
    	  
    	  // Set payment status to false initially
    	  ticket.setPaymentDone(false);

          String output = userService.bookTicket(ticket);
          return ResponseEntity.ok(output);
      }
      catch (Exception e) 
      {
    	  return ResponseEntity.status(500).body("Failed to Book Ticket: " + e.getMessage());
	  }
   }

   @GetMapping("/mytickets/{userId}")
   public ResponseEntity<List<Ticket>> getUserTickets(@PathVariable int userId) 
   {
       List<Ticket> tickets = userService.getUserTickets(userId);
       return ResponseEntity.ok(tickets);
   }
   
   @PostMapping("/addreview")
   public ResponseEntity<String> addReview(@RequestBody Review review)
   {
       try
       {
           String output = userService.addReview(review);
           return ResponseEntity.ok(output);
       }
       catch(Exception e)
       {
           return ResponseEntity.status(500).body("Failed to Add Review ... !!");
       }
   }
   
   @GetMapping("/reviews/{eventId}")
   public ResponseEntity<List<Review>> getEventReviews(@PathVariable int eventId)
   {
       List<Review> reviews = userService.getEventReviews(eventId);
       return ResponseEntity.ok(reviews);
   }
}