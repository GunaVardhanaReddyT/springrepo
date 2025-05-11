package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.model.Movie;

public interface MovieService 
{
  public Movie getMovieById(int movieId);
  public List<Movie> getAllMovies();
  public List<Movie> getMoviesByGenre(String genre);
  public List<Movie> searchMovies(String keyword);
  public List<Movie> getLatestMovies();
  public List<Movie> getMoviesByRating(double minRating);
}