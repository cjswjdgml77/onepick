package com.icia.zboard3.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.icia.zboard3.entity.*;

public interface AuthorityDao {
	@Insert("insert into authorities(username,authority) values(#{username},#{authority})")
	public int insert(@Param("username") String username,@Param("authority") String authority);
	
	@Select("select * from authorities where username=#{username}")
	public List<Authority> findByUsername(String username);
}
