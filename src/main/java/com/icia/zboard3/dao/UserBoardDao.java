package com.icia.zboard3.dao;

import org.apache.ibatis.annotations.*;

import lombok.*;

public interface UserBoardDao {
	@Select("select count(*) from users_board where bno=#{bno} and username=#{username} and rownum=1")
	public Boolean findExists(@Param("username") String username,@Param("bno") Integer bno);
	
	@Insert("insert into users_board values (#{username}, #{bno}) ")
	public Integer insert(@Param("username") String username, @Param("bno")  Integer bno);
	
}
