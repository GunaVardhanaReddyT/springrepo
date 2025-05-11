package com.klef.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.klef.sdp.model.Payment;
import com.klef.sdp.model.Ticket;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>
{
  public Payment findByPaymentReference(String paymentReference);
  
  public Payment findByTicket(Ticket ticket);
  
  public List<Payment> findByStatus(String status);
  
  @Modifying
  @Transactional
  @Query("UPDATE Payment p SET p.status = ?1 WHERE p.id = ?2")
  public int updatePaymentStatus(String status, int id);
  
  @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = 'COMPLETED'")
  public long getSuccessfulPaymentsCount();
  
  @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED'")
  public double getTotalRevenue();
}