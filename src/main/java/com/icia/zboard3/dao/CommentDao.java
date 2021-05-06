package com.icia.zboard3.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.icia.zboard3.entity.*;
import com.icia.zboard3.entity.Comment.*;

import lombok.*;

public interface CommentDao {

	public List<Comment> findFirstPage(@Param("endRowNum") Integer endRowNum,@Param("bno") Integer bno);

	public Integer insert(Comment comment);

	public List<Comment> findAll(@Param("map") Map<String, Integer> map,@Param("bno") Integer bno);

	public Optional<Comment> findByCno(Integer cno);

	public Integer delete(Integer cno);

	public Integer deleteByBno(Integer bno);

	public List<Comment> findByWriter(@Param("writer")String writer,@Param("bno") Integer bno);

	public Integer update(Comment comment);
	
	
}
