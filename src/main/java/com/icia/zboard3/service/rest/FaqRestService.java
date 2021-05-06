package com.icia.zboard3.service.rest;

import java.io.*;
import java.util.*;

import javax.validation.*;

import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.dto.*;
import com.icia.zboard3.entity.*;
import com.icia.zboard3.exception.*;
import com.icia.zboard3.util.*;

import lombok.*;
@RequiredArgsConstructor
@Service
public class FaqRestService {
	private final ModelMapper modelMapper;
	private final UserDao userDao;
	private final CommentDao commentDao;
	private final FaqBoardDao boardDao;
	private final AttachmentDao attachmentDao;
	private final PagingUtil pagingUtil;
	private final UserBoardDao userBoardDao;
	public CKResponse ckImageUpload(MultipartFile upload) {
		if(upload!=null && upload.isEmpty()==false) {
			if(upload.getContentType().toLowerCase().startsWith("image/")) {
				String originalFileName = upload.getOriginalFilename();
				String extension  = originalFileName.substring(originalFileName.lastIndexOf("."));
				String imageName = UUID.randomUUID().toString()+extension;
				File file = new File(Zboard3Constant.TempFolder, imageName);
				try {
					upload.transferTo(file);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				return new CKResponse(1, imageName , Zboard3Constant.TempPath + imageName );
			}
		}
		return null;
	}

	public CommentDto.Rest writeComment( CommentDto.Write dto, String username) {
		FaqBoard board = boardDao.findByBno(dto.getBno()).orElseThrow(()-> new FaqBoardJob.BoardNotFoundException());
		String profile = userDao.findById(username).get().getProfile();
		Comment comment = modelMapper.map(dto, Comment.class);
		comment.setWriter(username);
		comment.setProfile(Zboard3Constant.ProfilePath+profile);
		commentDao.insert(comment);
		
		boardDao.update(FaqBoard.builder().bno(dto.getBno()).commentCnt(board.getCommentCnt()+1).build());

		int endRowNum = board.getCommentCnt()>10 ?  10: board.getCommentCnt()+1;
		if (endRowNum>10)
			endRowNum=10;
		
		int nextCommentPage = board.getCommentCnt()+1>10 ? 2:0;
		System.out.println(nextCommentPage);
		return new CommentDto.Rest(commentDao.findFirstPage(endRowNum, dto.getBno()),nextCommentPage);
	}

	public CommentDto.Rest nextComment (Integer pageno, Integer bno) {
		 FaqBoard board = boardDao.findByBno(bno).orElseThrow(()-> new FaqBoardJob.BoardNotFoundException());
		 Map<String, Integer> map = pagingUtil.getRowNum(pageno,board.getCommentCnt());
		 // pageno  2    endRownum20
		 // pageno  3    endRowNum30  댓글개수 35
		 // pageno  0    endRowNum40 
		 int commentNextPage = board.getCommentCnt()> map.get("endRowNum") ? pageno+1:0;
		 System.out.println("=====================================================");
		 System.out.println(pageno);
		 System.out.println(map.get("endRowNum"));
		 System.out.println(commentNextPage);
		 return new CommentDto.Rest(commentDao.findAll(map,bno),commentNextPage);
	}

	public CommentDto.Rest deleteComment(@NonNull Integer bno, @NonNull Integer cno, String username) {
		FaqBoard board =  boardDao.findByBno(bno).orElseThrow(()-> new FaqBoardJob.BoardNotFoundException());
		Comment comment = commentDao.findByCno(cno).orElseThrow(()->new FaqBoardJob.CommentNotFoundException());
		if(comment.getWriter().equals(username)==false) 
			throw new FaqBoardJob.IllegalAccessException();
		commentDao.delete(cno);
		boardDao.update(FaqBoard.builder().bno(bno).deleteCommentCnt(board.getDeleteCommentCnt()+1).commentCnt(board.getCommentCnt()-1).build());
		int endRownum = board.getCommentCnt()>10 ? 10:board.getCommentCnt();
		int commentNextPage = board.getCommentCnt()-1>10 ? 2:0;
		return new CommentDto.Rest(commentDao.findFirstPage(endRownum, bno),commentNextPage);
	}

	public List<Attachment> deleteAttachment(@NonNull Integer ano, @NonNull Integer bno, String username) {
		FaqBoard board = boardDao.findByBno(bno).orElseThrow(()-> new FaqBoardJob.BoardNotFoundException());
		Attachment attachment = attachmentDao.findByAno(ano).orElseThrow(()-> new FaqBoardJob.AttachmentNotFoundException());
		if(attachment.getWriter().equals(username)==false)
			throw new FaqBoardJob.IllegalAccessException();
		
		attachmentDao.delete(ano);
		File file = new File(Zboard3Constant.AttachmentFolder, attachment.getSaveFileName());
		if(file.exists())
			file.delete();
		boardDao.update(FaqBoard.builder().bno(bno).attachmentCnt(board.getAttachmentCnt()-1).build());
		return attachmentDao.findAllByBno(bno);
	}

	public Integer goodOrBad(@NonNull Integer bno, Boolean isGood, String username) {
		FaqBoard board = boardDao.findByBno(bno).orElseThrow(()-> new FaqBoardJob.BoardNotFoundException());
		if(board.getWriter().equals(username)==true)
			throw new FaqBoardJob.IllegalAccessException();
		System.out.println("==============================");
		System.out.println(isGood);
		
		if(userBoardDao.findExists(username,bno)) {
			return isGood == true ? board.getGoodCnt():board.getBadCnt();
		}
		userBoardDao.insert(username,bno);
		if(isGood==true) {
			boardDao.update(FaqBoard.builder().bno(bno).goodCnt(board.getGoodCnt()+1).build());
			return board.getGoodCnt()+1;
		}else {
			boardDao.update(FaqBoard.builder().bno(bno).badCnt(board.getBadCnt()+1).build());
			return board.getBadCnt()+1;
		}
	}

	public Attachment findAttachmentByAno(Integer ano) {
		return attachmentDao.findByAno(ano).orElseThrow(()-> new FaqBoardJob.AttachmentNotFoundException());
	}
}
