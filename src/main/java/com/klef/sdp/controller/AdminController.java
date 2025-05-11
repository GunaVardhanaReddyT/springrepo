package com.klef.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.model.Admin;
import com.klef.sdp.model.User;
import com.klef.sdp.model.EventManager;
import com.klef.sdp.model.TheatreOwner;
import com.klef.sdp.model.Event;
import com.klef.sdp.model.Movie;
import com.klef.sdp.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController 
{
  @Autowired
  private AdminService adminService;
  
  @PostMapping("/checkadminlogin")
  public ResponseEntity<?> checkadminlogin(@RequestBody Admin admin)
  {
	  try 
      {
          Admin a = adminService.checkadminlogin(admin.getUsername(), admin.getPassword());

          if (a != null) 
          {
              return ResponseEntity.ok(a); // if login is successful
          } 
          else 
          {
              return ResponseEntity.status(401).body("Invalid Username or Password"); // if login is fail
          }
      } 
      catch (Exception e) 
      {
    	  System.out.println(e.getMessage()); // check the error in the console using this for debugging purpose
    	  
          return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
      }
  }
  
  @GetMapping("/viewallusers")
  public ResponseEntity<List<User>> viewallusers()
  {
	 List<User> users = adminService.viewallusers();
	 
	 return ResponseEntity.ok(users); // 200 - success
  }
  
  @PostMapping("/addeventmanager")
  public ResponseEntity<String> addeventmanager(@RequestBody EventManager manager)
  {
	   try
	   {
		  String output = adminService.addeventmanager(manager);
		  return ResponseEntity.ok(output); // 200 - success
	   }
	   catch(Exception e)
	   {
		   return ResponseEntity.status(500).body("Failed to Add Event Manager ... !!"); 
	   }
  }
  
  @GetMapping("/viewalleventmanagers")
  public ResponseEntity<List<EventManager>> viewalleventmanagers()
  {
	 List<EventManager> eventmanagers = adminService.viewalleventmanagers();
	 
	 return ResponseEntity.ok(eventmanagers); // 200 - success
  }
  
  @GetMapping("/viewalltheatreowners")
  public ResponseEntity<List<TheatreOwner>> viewalltheatreowners()
  {
	 List<TheatreOwner> theatreowners = adminService.viewalltheatreowners();
	 
	 return ResponseEntity.ok(theatreowners); // 200 - success
  }
  
  @PutMapping("/verifyTheatreOwner")
  public ResponseEntity<String> verifyTheatreOwner(@RequestParam int ownerId, @RequestParam boolean status)
  {
	  try
	   {
		  String output = adminService.verifyTheatreOwner(ownerId, status);
		  return ResponseEntity.ok(output);
	   }
	   catch(Exception e)
	   {
		    return ResponseEntity.status(500).body("Failed to Update Theatre Owner Verification Status ... !!"); 
	   }
  }
  
  @DeleteMapping("/deleteuser")
  public ResponseEntity<String> deleteuser(@RequestParam int userId)
  {
	  try
	   {
		  String output = adminService.deleteuser(userId);
		  return ResponseEntity.ok(output);
	   }
	   catch(Exception e)
	   {
		    return ResponseEntity.status(500).body("Failed to Delete User ... !!"); 
	   }
  }
  
  @DeleteMapping("/deleteeventmanager")
  public ResponseEntity<String> deleteeventmanager(@RequestParam int managerId)
  {
	  try
	   {
		  String output = adminService.deleteeventmanager(managerId);
		  return ResponseEntity.ok(output);
	   }
	   catch(Exception e)
	   {
		    return ResponseEntity.status(500).body("Failed to Delete Event Manager ... !!"); 
	   }
  }
  
  @DeleteMapping("/deletetheatreowner")
  public ResponseEntity<String> deletetheatreowner(@RequestParam int ownerId)
  {
	  try
	   {
		  String output = adminService.deletetheatreowner(ownerId);
		  return ResponseEntity.ok(output);
	   }
	   catch(Exception e)
	   {
		    return ResponseEntity.status(500).body("Failed to Delete Theatre Owner ... !!"); 
	   }
  }
  
  @GetMapping("/viewallevents")
  public ResponseEntity<List<Event>> viewallevents()
  {
	 List<Event> events = adminService.viewallevents();
	 
	 return ResponseEntity.ok(events); // 200 - success
  }
  
  @PutMapping("/approveevent")
  public ResponseEntity<String> approveEvent(@RequestParam int eventId, @RequestParam boolean status)
  {
	  try
	   {
		  String output = adminService.approveEvent(eventId, status);
		  return ResponseEntity.ok(output);
	   }
	   catch(Exception e)
	   {
		    return ResponseEntity.status(500).body("Failed to Update Event Approval Status ... !!"); 
	   }
  }
  
  @GetMapping("/viewallmovies")
  public ResponseEntity<List<Movie>> viewallmovies()
  {
	 List<Movie> movies = adminService.viewallmovies();
	 
	 return ResponseEntity.ok(movies); // 200 - success
  }
  
  @DeleteMapping("/deletemovie")
  public ResponseEntity<String> deletemovie(@RequestParam int movieId)
  {
	  try
	   {
		  String output = adminService.deletemovie(movieId);
		  return ResponseEntity.ok(output);
	   }
	   catch(Exception e)
	   {
		    return ResponseEntity.status(500).body("Failed to Delete Movie ... !!"); 
	   }
  }
  
  @GetMapping("/usercount")
  public ResponseEntity<Long> getUserCount()
  {
      long count = adminService.getUserCount();
      return ResponseEntity.ok(count);
  }

  @GetMapping("/eventmanagercount")
  public ResponseEntity<Long> getEventManagerCount()
  {
      long count = adminService.getEventManagerCount();
      return ResponseEntity.ok(count);
  }
  
  @GetMapping("/theatreownercount")
  public ResponseEntity<Long> getTheatreOwnerCount()
  {
      long count = adminService.getTheatreOwnerCount();
      return ResponseEntity.ok(count);
  }

  @GetMapping("/eventcount")
  public ResponseEntity<Long> getEventCount()
  {
      long count = adminService.getEventCount();
      return ResponseEntity.ok(count);
  }
  
  @GetMapping("/moviecount")
  public ResponseEntity<Long> getMovieCount()
  {
      long count = adminService.getMovieCount();
      return ResponseEntity.ok(count);
  }
  
  @GetMapping("/ticketcount")
  public ResponseEntity<Long> getTicketCount()
  {
      long count = adminService.getTicketCount();
      return ResponseEntity.ok(count);
  }
  
  @GetMapping("/totalrevenue")
  public ResponseEntity<Double> getTotalRevenue()
  {
      double revenue = adminService.getTotalRevenue();
      return ResponseEntity.ok(revenue);
  }
}