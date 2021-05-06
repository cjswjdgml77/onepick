package com.icia.zboard3.dao;

import java.util.*;

import org.springframework.security.core.userdetails.User.*;

import com.icia.zboard3.entity.*;

public interface UserDao {
	public Boolean existsById(String username);
	
	public Boolean existsByEmail(String email);
	
	public Integer insert(User user);

	public Optional<User> findById(String username);

	public Optional<User> findByEmail(String email);

	public Optional<User> findByCheck(String checkCode);

	public Integer update(User user);

	public Integer joinCheck(String username);

	public Integer delete(String username);

}







































