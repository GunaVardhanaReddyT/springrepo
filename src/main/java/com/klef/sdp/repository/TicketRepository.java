package com.klef.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.klef.sdp.model.Event;
import com.klef.sdp.model.Ticket;
import com.klef.sdp.model.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findByUser(User user);
    
    public List<Ticket> findByEvent(Event event);
    
    public Ticket findByTicketNumber(String ticketNumber);
    
    public Ticket findByBookingReference(String bookingReference);
    
    public List<Ticket> findByStatus(String status);
    
    @Query("SELECT t FROM Ticket t WHERE t.event.manager.id = ?1")
    public List<Ticket> getTicketsByManager(int managerId);
    
    @Modifying
    @Transactional
    @Query("UPDATE Ticket t SET t.status = ?1 WHERE t.id = ?2")
    public int updateStatusById(String status, int id);
    
    @Modifying
    @Transactional
    @Query("UPDATE Ticket t SET t.paymentDone = ?1 WHERE t.id = ?2")
    public int updatePaymentStatus(boolean status, int id);
    
    @Query("SELECT SUM(t.totalAmount) FROM Ticket t WHERE t.paymentDone = true")
    public double getTotalRevenue();
    
    // New method to fetch tickets for events of category MOVIE
    @Query("SELECT t FROM Ticket t WHERE t.event.category = 'MOVIE'")
    public List<Ticket> getTicketsByTheatreOwner(int ownerId);
}