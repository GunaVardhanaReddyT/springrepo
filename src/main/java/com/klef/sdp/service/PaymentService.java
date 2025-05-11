package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.model.Payment;

public interface PaymentService 
{
  public String processPayment(Payment payment);
  public Payment getPaymentById(int paymentId);
  public Payment getPaymentByReference(String reference);
  public Payment getPaymentByTicketId(int ticketId);
  public List<Payment> getPaymentsByStatus(String status);
  public String updatePaymentStatus(int paymentId, String status);
}