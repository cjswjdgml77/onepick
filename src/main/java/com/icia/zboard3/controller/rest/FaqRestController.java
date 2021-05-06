package com.icia.zboard3.controller.rest;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Path;
import java.security.*;
import java.util.*;

import javax.validation.*;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.binding.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import com.icia.zboard3.dto.*;
import com.icia.zboard3.entity.*;
import com.icia.zboard3.service.rest.*;
import com.icia.zboard3.util.*;

import lombok.*;
@RequiredArgsConstructor
@RestController
public class FaqRestController {
	private final FaqRestService service;
	
	//CK 이미지 업로드
	@Secured("ROLE_USER")
	@PostMapping(value="/faq/image", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CKResponse> ckImageUpload(MultipartFile upload){
		CKResponse ckResponse = service.ckImageUpload(upload);
		return ResponseEntity.ok(ckResponse);
	}
	//댓글쓰기
	@Secured("ROLE_USER")
	@PostMapping(value="/comments" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDto.Rest> writeComment(@Valid CommentDto.Write dto, BindingResult bindingResult, Principal principal) throws BindException{
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		return ResponseEntity.ok(service.writeComment(dto,principal.getName()));
	}
	//다음 댓글출력
	@GetMapping(value="/comments/next", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDto.Rest> nextComment(@NonNull Integer pageno, Integer bno){
		return ResponseEntity.ok(service.nextComment(pageno,bno));
	}
	//댓글 삭제
	@Secured("ROLE_USER")
	@DeleteMapping(value="/comments/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDto.Rest> deleteComment(@NonNull Integer bno, @NonNull Integer cno, Principal principal){
		return ResponseEntity.ok(service.deleteComment(bno,cno,principal.getName()));
	}
	//첨부파일 삭제
	@Secured("ROLE_USER")
	@DeleteMapping(value="/attachments/{ano}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Attachment>> deleteAttachment(@PathVariable Integer ano, @NonNull Integer bno,Principal principal){
		return ResponseEntity.ok(service.deleteAttachment(ano,bno,principal.getName()));
	}
	//첨부파일 읽기
	@GetMapping(value="/attachments/{ano}",produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> viewAttachment(@PathVariable Integer ano) throws IOException{
		Attachment attachment = service.findAttachmentByAno(ano);
		String originalFileName = attachment.getOriginalFileName();
		String savedName = attachment.getSaveFileName(); 
		File file = new File(Zboard3Constant.AttachmentFolder,savedName);
		HttpHeaders headers = new HttpHeaders();
		if(attachment.getIsImage()) {
			String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toUpperCase();
			MediaType type = MediaType.IMAGE_JPEG;
			if(extension.equals("PNG"))
				type = MediaType.IMAGE_PNG;
			if(extension.equals("GIF"))
				type = MediaType.IMAGE_GIF;
			headers.setContentType(type);
			headers.add("Content-Disposition", "inline;filename=" + originalFileName);
		}else {
			Path path = Paths.get(Zboard3Constant.AttachmentFolder+savedName);
			String contentType = Files.probeContentType(path);
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);
			headers.add("Content-Disposition", "attachment;filename=" + originalFileName);
		}
		try {
				System.out.println(headers);
				return ResponseEntity.ok().headers(headers).body(Files.readAllBytes(file.toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	// 글 추천 비추천
	@Secured("ROLE_USER")
	@PatchMapping(value="/board/goodOrBad", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> goodOrBad(@NonNull Integer bno, Boolean isGood, Principal principal){
		Integer result = service.goodOrBad(bno,isGood,principal.getName());
		return ResponseEntity.ok(result);
	}
}
