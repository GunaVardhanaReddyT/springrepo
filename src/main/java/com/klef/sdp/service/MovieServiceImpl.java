package com.klef.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.model.Movie;
import com.klef.sdp.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService
{
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public Movie getMovieById(int movieId) 
	{
		return movieRepository.findById(movieId).orElse(null);
	}

	@Override
	public List<Movie> getAllMovies() 
	{
		return movieRepository.findByActive(true);
	}

	@Override
	public List<Movie> getMoviesByGenre(String genre) 
	{
		return movieRepository.findByGenre(genre);
	}

	@Override
	public List<Movie> searchMovies(String keyword) 
	{
		return movieRepository.searchMoviesByTitle(keyword);
	}

	@Override
	public List<Movie> getLatestMovies() 
	{
		return movieRepository.findLatestMovies();
	}

	@Override
	public List<Movie> getMoviesByRating(double minRating) 
	{
		return movieRepository.findMoviesByMinimumRating(minRating);
	}
}