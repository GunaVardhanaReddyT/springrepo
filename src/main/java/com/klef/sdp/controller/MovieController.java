package com.klef.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.model.Movie;
import com.klef.sdp.service.MovieService;

@RestController
@RequestMapping("/movies")
@CrossOrigin("*")
public class MovieController 
{
   @Autowired
   private MovieService movieService;
   
   @GetMapping("/all")
   public ResponseEntity<List<Movie>> getAllMovies()
   {
       List<Movie> movies = movieService.getAllMovies();
       return ResponseEntity.ok(movies);
   }
   
   @GetMapping("/{id}")
   public ResponseEntity<?> getMovieById(@PathVariable int id)
   {
       try
       {
           Movie movie = movieService.getMovieById(id);
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
   
   @GetMapping("/genre/{genre}")
   public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable String genre)
   {
       List<Movie> movies = movieService.getMoviesByGenre(genre);
       return ResponseEntity.ok(movies);
   }
   
   @GetMapping("/search")
   public ResponseEntity<List<Movie>> searchMovies(@RequestParam String keyword)
   {
       List<Movie> movies = movieService.searchMovies(keyword);
       return ResponseEntity.ok(movies);
   }
   
   @GetMapping("/latest")
   public ResponseEntity<List<Movie>> getLatestMovies()
   {
       List<Movie> movies = movieService.getLatestMovies();
       return ResponseEntity.ok(movies);
   }
   
   @GetMapping("/rating/{minRating}")
   public ResponseEntity<List<Movie>> getMoviesByRating(@PathVariable double minRating)
   {
       List<Movie> movies = movieService.getMoviesByRating(minRating);
       return ResponseEntity.ok(movies);
   }
}