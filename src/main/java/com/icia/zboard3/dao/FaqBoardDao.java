package com.icia.zboard3.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.icia.zboard3.entity.*;

import lombok.*;

public interface FaqBoardDao {

	public Integer count(String writer);

	public List<Map> findAll(Map<String, Integer> map);

	public List<Map> findAllByWriter(@Param("map") Map<String, Integer> map,@Param("writer")  String writer);

	public Integer insert(FaqBoard board);

	public Optional<FaqBoard> findByBno(Integer bno);

	public Integer update(FaqBoard board);

	public Integer delete(Integer bno);

}
