package com.icia.zboard3.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.icia.zboard3.entity.*;

import lombok.*;

public interface AttachmentDao {
	@Insert("insert into attachment values (attachment_seq.nextval, #{bno}, #{writer}, #{originalFileName},#{saveFileName}, #{length}, #{isImage}) ")
	public Integer insert(Attachment attachment);
	
	@Select("select * from attachment where bno=#{bno}")
	public List<Attachment> findAllByBno(Integer bno);
	
	@Delete("delete from attachment where bno=#{bno} and rownum=1")
	public Integer deleteByBno( Integer bno);
	
	@Select("select * from attachment where ano=#{ano}")
	public Optional<Attachment> findByAno(Integer ano);
	
	@Delete("delete from attachment where ano=#{ano} and rownum=1")
	public Integer delete(Integer ano);

	
}
