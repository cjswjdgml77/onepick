package com.icia.zboard3.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.icia.zboard3.entity.*;

import lombok.*;

public interface ProductDao {
	public List<Product> findAll();
	
	public Optional<Product> findByPno( Integer pno);
	
	public List<Product> findByBottoms(List<Integer> cnos);
	
	public List<Product> findByTops(List<Integer> cnos);
}
