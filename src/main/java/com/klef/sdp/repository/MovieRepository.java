package com.klef.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.klef.sdp.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>
{
  public List<Movie> findByGenre(String genre);
  
  public List<Movie> findByActive(boolean active);
  
  @Query("SELECT m FROM Movie m WHERE m.title LIKE %?1%")
  public List<Movie> searchMoviesByTitle(String title);
  
  @Query("SELECT m FROM Movie m WHERE m.rating >= ?1")
  public List<Movie> findMoviesByMinimumRating(double rating);
  
  @Query("SELECT m FROM Movie m ORDER BY m.releaseDate DESC")
  public List<Movie> findLatestMovies();
  
  @Modifying
  @Transactional
  @Query("UPDATE Movie m SET m.active = ?1 WHERE m.id = ?2")
  public int updateActiveStatus(boolean active, int id);
  
  @Modifying
  @Transactional
  @Query("UPDATE Movie m SET m.rating = ?1 WHERE m.id = ?2")
  public int updateMovieRating(double rating, int id);
  
  @Query("SELECT COUNT(m) FROM Movie m")
  public long getMovieCount();
}