package com.icia.zboard3.service.mvc;

import java.io.*;
import java.nio.file.*;
import java.time.format.*;
import java.util.*;

import javax.validation.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;
import org.springframework.web.multipart.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.dto.*;
import com.icia.zboard3.dto.FaqBoardDto.*;
import com.icia.zboard3.entity.*;
import com.icia.zboard3.entity.Comment;
import com.icia.zboard3.exception.*;
import com.icia.zboard3.util.*;

import lombok.*;
@RequiredArgsConstructor
@Service
public class FaqBoardService {
	private final FaqBoardDao faqBoardDao;
	private final UserDao userDao;
	private final PagingUtil<FaqBoardDto.ListView> pagingUtil;
	private final ModelMapper modelMapper;
	private final AttachmentDao attachmentDao;
	private final CommentDao commentDao;
	
	@Autowired
	@Qualifier("timeFormatter")
	private DateTimeFormatter dtf;
	
	public Page<FaqBoardDto.ListView> list(Integer pageno,String writer){
		int count = faqBoardDao.count(writer);
		Page<FaqBoardDto.ListView> page = pagingUtil.getPage(pageno, count);
		//writer가 있으면 그 사용자 글만 보이게 하기
		List<Map> alist=null;
		if(writer==null)
			alist = faqBoardDao.findAll(pagingUtil.getRowNum(pageno, count));
		else//없으면 전체 페이지
			alist = faqBoardDao.findAllByWriter(pagingUtil.getRowNum(pageno, count),writer);
		
		List<FaqBoardDto.ListView> list = new ArrayList<>();
		alist.forEach(map->list.add(modelMapper.map(map, FaqBoardDto.ListView.class)));
		System.out.println(list);
		return page.setList(list);
	}
	@Transactional
	public void write(FaqBoardDto.Write dto, List<MultipartFile> attachments, String username) {
		FaqBoard board = modelMapper.map(dto, FaqBoard.class);
		board.setWriter(username);
		Document document = Jsoup.parseBodyFragment(dto.getContent());
		Elements element = document.getElementsByTag("img");
		if(element.isEmpty()==false) {
			for(Element e:element) {
				String src = e.attr("src");
				String fileName = src.substring(src.lastIndexOf("/")+1);
				File originFile = new File(Zboard3Constant.TempFolder,fileName);
				File newFile = new File(Zboard3Constant.ImageFolder,fileName);
				try {
					FileCopyUtils.copy(Files.readAllBytes(originFile.toPath()), newFile);
					originFile.delete();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		String content = dto.getContent().replaceAll(Zboard3Constant.CK_PATTERN, Zboard3Constant.CK_REPLACE);
		board.setContent(content);
		board.setAttachmentCnt(attachments.size());
		faqBoardDao.insert(board);
		if(attachments.isEmpty()==false) {
			for(MultipartFile file:attachments) {
			String saveFileName = System.currentTimeMillis() + "-"+ file.getOriginalFilename();
			File saveFile = new File(Zboard3Constant.AttachmentFolder,saveFileName);
		
			try {
				FileCopyUtils.copy(file.getBytes(), saveFile );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Boolean isImage = file.getContentType().toLowerCase().startsWith("image/");
			Attachment attachment = new Attachment(0,board.getBno(), file.getOriginalFilename(), saveFileName, username, file.getSize(),isImage);
			System.out.println("============================");
			System.out.println(attachment);
			attachmentDao.insert(attachment);
			}
		}
	}
	public FaqBoardDto.Read read(Integer bno, String username) {
		FaqBoard board = faqBoardDao.findByBno(bno).orElseThrow(()->new FaqBoardJob.BoardNotFoundException());
		FaqBoardDto.Read dto = modelMapper.map(board, FaqBoardDto.Read.class);
		dto.setWriteTimeString(dtf.format(board.getWriteTime()));
		if(username!=null && board.getWriter().equals(username)==false) {
			int readCnt = board.getReadCnt()+1;
			faqBoardDao.update(FaqBoard.builder().bno(bno).readCnt(readCnt).build());
			dto.setReadCnt(readCnt);
		}
		// 댓글 가져오기 
				// 댓글이 10개이하면 댓글의 개수(board.getCommentCnt())만큼 읽어온다. nextCommentPage는 0
				// 댓글이 10개이상이면 10개 읽어온다. nextCommentPage는 2
		if(board.getCommentCnt()>0) {
			int commentCnt = board.getCommentCnt();
			int endRowNum = commentCnt>0 ? 10 : 0;
			int nextCommentPage = commentCnt>10? 2:0;
			System.out.println("================================");
			System.out.println(nextCommentPage);
			dto.setNextCommentPage(nextCommentPage);
			List<Comment> cList = commentDao.findFirstPage(endRowNum, bno);
			List<CommentDto.Read> comments = new ArrayList<>();
			for(Comment c:cList) {
				CommentDto.Read r = modelMapper.map(c, CommentDto.Read.class);
				r.setIsWriter(c.getWriter().equals(username));
				r.setWriteTimeString(dtf.format(c.getWriteTime()));
				String newProfile= userDao.findById(username).get().getProfile();
				List<Comment> comment= commentDao.findByWriter(username,bno);
				for(Comment old:comment ) {
					String oldProfile = old.getProfile().substring(old.getProfile().lastIndexOf("/")+1);
					if(newProfile.equals(oldProfile)==false)
						r.setProfile(Zboard3Constant.ProfilePath+newProfile);
				}
				comments.add(r);
			}
			dto.setComments(comments);
		}
		if(board.getAttachmentCnt()>0)
			dto.setAttachments(attachmentDao.findAllByBno(bno));
	return dto;
	}
	public void update(@Valid FaqBoardDto.Update dto, String username) {
		FaqBoard board = faqBoardDao.findByBno(dto.getBno()).orElseThrow(()-> new FaqBoardJob.BoardNotFoundException());
		if(board.getWriter().equals(username)==false)
			throw new FaqBoardJob.IllegalAccessException();
		Elements oldImg = Jsoup.parseBodyFragment(board.getContent()).getElementsByTag("src");
		Elements newImg = Jsoup.parseBodyFragment(dto.getContent()).getElementsByTag("src");
		
		List<String> oldImgList = new ArrayList<>();
		List<String> newImgList = new ArrayList<>();
		
		for(Element e:oldImg)
			oldImgList.add(e.attr("src").substring(e.attr("src").lastIndexOf("/")+1));
		for(Element e:newImg)
			newImgList.add(e.attr("src").substring(e.attr("src").lastIndexOf("/")+1));
		
		for(String fileName:oldImgList) {
			if(newImgList.contains(fileName)==false)
				new File(Zboard3Constant.ImageFolder,fileName).delete();
		}
		for(String fileName:newImgList) {
			File origin = new File(Zboard3Constant.TempFolder,fileName);
			File dest = new File(Zboard3Constant.ImageFolder,fileName);
			try {
				if(origin.exists()) {
				FileCopyUtils.copy(Files.readAllBytes(origin.toPath()), dest);
				origin.delete();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		FaqBoard result = FaqBoard.builder().bno(dto.getBno()).title(dto.getTitle()).build();
		result.setContent(dto.getContent().replaceAll(Zboard3Constant.CK_PATTERN, Zboard3Constant.CK_REPLACE));
		faqBoardDao.update(result);
	}
	public void delete(@NonNull Integer bno, String username) {
		FaqBoard board = faqBoardDao.findByBno(bno).orElseThrow(()-> new FaqBoardJob.BoardNotFoundException());
		if(board.getWriter().equals(username)==false)
				throw new FaqBoardJob.IllegalAccessException();
		List<Attachment> aList = attachmentDao.findAllByBno(bno);
		
		attachmentDao.deleteByBno(bno);
		commentDao.deleteByBno(bno);
		faqBoardDao.delete(bno);
		
		if(aList.isEmpty()==false) {
			for(Attachment a:aList) {
				File file = new File(Zboard3Constant.AttachmentFolder, a.getSaveFileName());
				file.delete();
			}
		}
		Document document = Jsoup.parseBodyFragment(board.getContent());
		Elements element = document.getElementsByTag("img");
		if(element.isEmpty()==false) {
			for(Element e:element) {
				String src = e.attr("src");
				String fileName = src.substring(src.lastIndexOf("/")+1);
				File file = new File(Zboard3Constant.ImageFolder,fileName);
				file.delete();
			}
		}
		
		
	}
	
}
