package com.icia.zboard3.service.rest;

import java.io.*;
import java.time.format.*;

import javax.validation.*;

import org.apache.commons.lang3.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;
import org.springframework.web.multipart.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.dto.*;
import com.icia.zboard3.dto.UserDto.*;
import com.icia.zboard3.entity.*;
import com.icia.zboard3.exception.*;
import com.icia.zboard3.util.*;

import lombok.*;
@RequiredArgsConstructor
@Service
public class UserRestService {
	private final UserDao userDao;
	private final AuthorityDao authorityDao;
	private final ModelMapper modelMapper;
	private final MailUtil mailUtil;
	private final CommentDao commentDao;
	private final PasswordEncoder passwordEncoder;
	
	@Qualifier("dateFormatter")
	@Autowired
	private DateTimeFormatter dtf;
 
	public void idAvailableCheck(String username) {
		if(userDao.existsById(username))
			throw new UserJob.IdExistException();;
	}
	public void emailAvailableCheck(String email) {
		if(userDao.existsByEmail(email))
			throw new UserJob.EmailExistException();
		
	}
	@Transactional
	public void join(UserDto.Join dto) {
		System.out.println(dto);
		User user =	modelMapper.map(dto, User.class);
		System.out.println(user);
		String passwordEncoded = passwordEncoder.encode(user.getPassword());
		String checkCode = RandomStringUtils.randomAlphanumeric(20);
		user.setCheckCode(checkCode);
		user.setPassword(passwordEncoded);
		user.setProfile("default.jpg");
		user.setLevels(Level.NORMAL);
		userDao.insert(user);
		authorityDao.insert(user.getUsername(), "ROLE_USER");
		mailUtil.joinMail("admin@naver.com", user.getEmail(), checkCode);
		
	}
	public String findUsername(String email) {
		User user = userDao.findByEmail(email).orElseThrow(()->new UserJob.UserNotFoundException());
		return user.getUsername();
	}
	public void changeIrum(String irum, String username) {
		userDao.findById(username).orElseThrow(()->new UserJob.UserNotFoundException());
		userDao.update(User.builder().username(username).irum(irum).build());
	}
	public void changePwd( UserDto.ChangePassword dto, String username) {
		User user = userDao.findById(username).orElseThrow(()->new UserJob.UserNotFoundException());
		if(passwordEncoder.matches(dto.getPassword(), user.getPassword())==false)
			throw new UserJob.PasswordCheckFailException();
		String encodedPwd2 = passwordEncoder.encode(dto.getNewPassword());
		userDao.update(User.builder().username(username).password(encodedPwd2).build());
	}
	public void update(UserDto.Update dto, MultipartFile profile, String username) {
		User user = userDao.findById(username).orElseThrow(()->new UserJob.UserNotFoundException());
		User param = User.builder().irum(dto.getIrum()).email(dto.getEmail()).username(username).build();
		if(profile!=null && profile.isEmpty()==false) {
			String originalFileName= profile.getOriginalFilename();
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String newProfileFileName = username + extension.toUpperCase();
			
			if(user.getProfile().equals("default.jpg")==false) {
				File oldProfile = new File(Zboard3Constant.ProfileFolder, user.getProfile());
				if(oldProfile.exists()==true)
					oldProfile.delete();
			}
			File newProfile = new File(Zboard3Constant.ProfileFolder,newProfileFileName);
			try {
				profile.transferTo(newProfile);
				param.setProfile(newProfileFileName);
				commentDao.update(Comment.builder().writer(username).profile(Zboard3Constant.ProfilePath+newProfileFileName).build());
			}  catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(dto.getPassword()!=null && dto.getNewPassword()!=null) {
			String endcodedPassword = passwordEncoder.encode(dto.getNewPassword());
			if(passwordEncoder.matches(dto.getPassword(),user.getPassword()))
					param.setPassword(endcodedPassword);
		}
		userDao.update(param);
	}
	public String joinday( String username) {
		User user = userDao.findById(username).orElseThrow(()-> new UserJob.UserNotFoundException());
		String joinday = dtf.format(user.getJoinday());
 		return joinday;
	}
	public void findPassword(UserDto.ResetPassword dto) {
		User user = userDao.findById(dto.getUsername()).orElseThrow(()-> new UserJob.UserNotFoundException());
		if(dto.getEmail().equals(user.getEmail())==false)
			throw new UserJob.FindPasswordFailException();
		
		String newPassword = RandomStringUtils.randomAlphabetic(10);
		String encodedPassword = passwordEncoder.encode(newPassword);
		userDao.update(User.builder().username(dto.getUsername()).password(encodedPassword).build());
		//mailUtil.resetPwd("admin223@naver.com", dto.getEmail(), newPassword);
	}	
	
}
