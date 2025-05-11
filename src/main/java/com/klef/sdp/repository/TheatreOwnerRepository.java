package com.klef.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.klef.sdp.model.TheatreOwner;

@Repository
public interface TheatreOwnerRepository extends JpaRepository<TheatreOwner, Integer>
{
  public TheatreOwner findByUsernameAndPassword(String username, String password);
  
  public TheatreOwner findByEmail(String email);
  
  public TheatreOwner findByUsername(String username);
  
  public List<TheatreOwner> findByVerified(boolean verified);
  
  @Modifying
  @Transactional
  @Query("UPDATE TheatreOwner t SET t.verified = ?1 WHERE t.id = ?2")
  public int updateVerificationStatus(boolean status, int id);
  
  @Query("SELECT COUNT(t) FROM TheatreOwner t")
  public long getTheatreOwnerCount();
}