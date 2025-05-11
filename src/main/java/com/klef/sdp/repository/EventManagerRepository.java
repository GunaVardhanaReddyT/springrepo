package com.klef.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.sdp.model.EventManager;

@Repository
public interface EventManagerRepository extends JpaRepository<EventManager, Integer>
{
  public EventManager findByUsernameAndPassword(String username, String password);
  
  public EventManager findByEmail(String email);
  
  public EventManager findByUsername(String username);
  
  @Query("SELECT COUNT(m) FROM EventManager m")
  public long getEventManagerCount();
}