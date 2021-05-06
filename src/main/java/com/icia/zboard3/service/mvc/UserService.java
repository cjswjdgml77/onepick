package com.icia.zboard3.service.mvc;


import java.time.format.*;

import javax.validation.*;

import org.apache.commons.lang3.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.format.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.dto.*;
import com.icia.zboard3.entity.User;
import com.icia.zboard3.exception.*;
import com.icia.zboard3.util.*;

import lombok.*;


@RequiredArgsConstructor
@Service
public class UserService {
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	
	@Autowired
	@Qualifier("dateFormatter")
	private DateTimeFormatter dtf;
	
	public void joinCheck(String checkCode) {
		User user = userDao.findByCheck(checkCode).orElseThrow(()->new UserJob.JoinCheckFailException());
		userDao.joinCheck(user.getUsername());
	}
	public void passwordCheck(String password, String username) {
		String encodedPassword = userDao.findById(username).orElseThrow(()->new UserJob.UserNotFoundException()).getPassword();
		if(passwordEncoder.matches(password, encodedPassword)==false) 
			throw new UserJob.PasswordCheckFailException();
	}
	public UserDto.Info info(String username) {
		User user = userDao.findById(username).orElseThrow(()-> new UserJob.UserNotFoundException());
		UserDto.Info dto =modelMapper.map(user, UserDto.Info.class);
		
		//조인데이
		dto.setBirthdayString(dtf.format(user.getBirthday())).setJoindayString(dtf.format(user.getJoinday())).setLevel(user.getLevels().name());
		
		return dto; 
	}
	public void resign(String username) {
		userDao.findById(username).orElseThrow(()-> new UserJob.UserNotFoundException());
		userDao.delete(username);
		
	}
}
