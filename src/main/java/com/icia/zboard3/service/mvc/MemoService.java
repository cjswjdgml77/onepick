package com.icia.zboard3.service.mvc;

import java.time.format.*;
import java.util.*;

import javax.validation.*;

import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.dto.*;
import com.icia.zboard3.dto.MemoDto.*;
import com.icia.zboard3.entity.*;
import com.icia.zboard3.exception.*;
import com.icia.zboard3.websocket.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class MemoService {
	private final MemoDao memoDao;
	private final MessageWebSocketHandler handler;
	private final ModelMapper modelMapper;
	
	@Autowired
	@Qualifier("timeFormatter")
	private DateTimeFormatter dtf;
	
	public void write(@Valid MemoDto.Write dto, String username) {
		Memo memo = modelMapper.map(dto, Memo.class).setSender(username);
		memoDao.insert(memo);
		handler.sendMessage(memo.getSender(), memo.getReceiver(), memo.getTitle());
	}
	
	public List<MemoDto.ListView> read(String username) {
		List<Memo> memo = memoDao.findByReceiver(username);
		List<MemoDto.ListView> list = new ArrayList<>();
		for(Memo m:memo) {
			MemoDto.ListView dto = modelMapper.map(m, MemoDto.ListView.class);
			dto.setWriteTimeString(dtf.format(m.getWriteTime()));
			list.add(dto);
		}
		return list;
	}

	public List<ListView> send(String username) {
		List<Memo> memo = memoDao.findBySender(username);
		List<MemoDto.ListView> list = new ArrayList<>();
		for(Memo m:memo) {
			MemoDto.ListView dto = modelMapper.map(m, MemoDto.ListView.class);
			dto.setWriteTimeString(dtf.format(m.getWriteTime()));
			list.add(dto);
		}
		return list;
	}
	public MemoDto.Read read(Integer mno) {
		Memo memo = memoDao.findById(mno).orElseThrow(FaqBoardJob.MemoNotFoundException::new);
		memoDao.setRead(mno);
		return modelMapper.map(memo, MemoDto.Read.class).setWriteTimeString(dtf.format((memo.getWriteTime())));
	}

	public void disabledBySender(List<Integer> list) {
		memoDao.disabledBySender(list);
	}
	
	public void disabledByReceiver(List<Integer> list) {
		memoDao.disabledByReceiver(list);
	}
	@Scheduled(cron="0 0 4 1/1 * ?")
	public void delete() {
		memoDao.delete();
	}
}
