package com.icia.zboard3.dto;

import java.security.*;
import java.time.*;

import javax.validation.constraints.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.*;
import org.springframework.validation.*;

import com.sun.istack.*;

import lombok.*;
import lombok.experimental.*;



public class UserDto {
	@Data
	public static class Update {
		private String email;
		private String irum;
		
		@Pattern(regexp="(?=.*[!@#$%^&*])^[A-Za-z0-9!@#$%^&*]{8,10}$", message="비밀번호는 특수문자를 포함하는 영숫자 8~10자입니다")
		private String password;
		
		@Pattern(regexp="(?=.*[!@#$%^&*])^[A-Za-z0-9!@#$%^&*]{8,10}$", message="비밀번호는 특수문자를 포함하는 영숫자 8~10자입니다")
		private String newPassword;
	}
	@Data
	public static class ChangePassword {
		@NotNull(message="비밀번호는 필수입력입니다")
		private String password;
		
		@NotNull(message="비밀번호는 필수입력입니다")
		@Pattern(regexp="(?=.*[!@#$%^&*])^[A-Za-z0-9!@#$%^&*]{8,10}$", message="비밀번호는 특수문자를 포함하는 영숫자 8~10자입니다")
		private String newPassword;
	}
	@Data
	public static class Join{
		private String username;
		private String irum;
		private String email;
		@DateTimeFormat(pattern="yyyy-MM-dd")
		private LocalDate birthday;
		private String password;
		
	}
	@Data
	@Accessors(chain=true)
	public static class Info{
		private String irum;
		private String email;
		private String birthdayString;
		private String joindayString;
		private String profile;
		private String level;
	}
	@Data
	public static class ResetPassword{
		@NotNull
		private String username;
		@NotNull
		private String email;
	}
}
