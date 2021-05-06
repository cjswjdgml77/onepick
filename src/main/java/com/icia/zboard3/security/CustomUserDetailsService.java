package com.icia.zboard3.security;

import java.util.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.entity.*;
import com.icia.zboard3.entity.User;

import lombok.*;
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	private final UserDao userDao;
	private final AuthorityDao authorityDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findById(username).orElseThrow(()-> new InternalAuthenticationServiceException("USER NOT FOUND"));
		Account account = Account.builder().username(user.getUsername()).password(user.getPassword()).isEnabled(user.getEnabled()).build();
		account.setAuthorities(getAuthorities(username));
		return account;
	}
	private Collection<GrantedAuthority> getAuthorities(String username) {
		List<Authority> authorities = authorityDao.findByUsername(username);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for(Authority authority:authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
		}
		return grantedAuthorities;
	}
	
	
	
}
