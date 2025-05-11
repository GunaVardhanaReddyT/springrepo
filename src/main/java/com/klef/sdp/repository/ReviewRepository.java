package com.klef.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.sdp.model.Event;
import com.klef.sdp.model.Review;
import com.klef.sdp.model.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>
{
  public List<Review> findByUser(User user);
  
  public List<Review> findByEvent(Event event);
  
  @Query("SELECT AVG(r.rating) FROM Review r WHERE r.event.id = ?1")
  public Double getAverageRatingForEvent(int eventId);
  
  @Query("SELECT r FROM Review r WHERE r.event.manager.id = ?1")
  public List<Review> getReviewsByManager(int managerId);
  
  @Query("SELECT r FROM Review r ORDER BY r.createdAt DESC")
  public List<Review> getRecentReviews();
  
  @Query("SELECT r FROM Review r WHERE r.rating >= ?1")
  public List<Review> getReviewsByMinimumRating(int minRating);
  
  @Query("SELECT COUNT(r) FROM Review r")
  public long getReviewCount();
}