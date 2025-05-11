package com.klef.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.klef.sdp.model.Event;
import com.klef.sdp.model.EventManager;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>
{
    public List<Event> findByCategory(String category);
    
    public List<Event> findByManager(EventManager manager);
    
    public List<Event> findByApproved(boolean approved);
    
    @Query("SELECT e FROM Event e WHERE e.title LIKE %?1%")
    public List<Event> searchEventsByTitle(String title);
    
    @Query("SELECT e FROM Event e WHERE e.capacity > e.availableSeats")
    public List<Event> findPopularEvents();
    
    @Modifying
    @Transactional
    @Query("UPDATE Event e SET e.approved = ?1 WHERE e.id = ?2")
    public int updateApprovalStatus(boolean status, int id);
    
    @Query("SELECT COUNT(e) FROM Event e")
    public long getEventCount();
}