package com.klef.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.sdp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
  public User findByUsernameAndPassword(String username, String password);
  
  public User findByEmail(String email);
  
  public User findByUsername(String username);
  
  @Query("SELECT COUNT(u) FROM User u")
  public long getUserCount();
}