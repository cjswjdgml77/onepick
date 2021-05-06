package com.icia.zboard3.util;

import java.util.*;

import org.springframework.stereotype.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.dto.*;
@Component
public class PagingUtil<T> {
	public Map<String,Integer> getRowNum(int pageno, int count){
		//					startRowNum			endRowNum
		// pageno 1				1					10
		// pageno 2 			11					20
		// pageno 3				21					30
// Board_PER_PAGE 10
// Page_PER_block 5
		int startRowNum = (pageno-1)*Zboard3Constant.BOARD_PER_PAGE+1;
		int endRowNum = pageno*Zboard3Constant.BOARD_PER_PAGE;
		if(endRowNum>count)
			endRowNum=count;
		Map<String, Integer> map = new HashMap<>();
		map.put("startRowNum", startRowNum);
		map.put("endRowNum", endRowNum);
		return map;
	}
	public Page<T> getPage(int pageno, int count){
		//   prev	start	end		next
//blockNo 0	  0		  1		 5		 6     1~5
		//1   5       6     10       11    6~10
		//2   10	  11    15       16	   11~15
		// countOfPage = 130 % 10 = 13;
		int countOfPage = count/Zboard3Constant.BOARD_PER_PAGE+1;
		System.out.println("=======================........====="+count);
		System.out.println(countOfPage);
		if(count%Zboard3Constant.BOARD_PER_PAGE==0)
			countOfPage--;
		
		if(pageno>countOfPage)
			pageno=countOfPage;
		
		int blockNo = (pageno-1)/Zboard3Constant.PAGE_PER_PAGE;
		int prev = blockNo*Zboard3Constant.PAGE_PER_PAGE;
		int start = prev +1;
		int end = prev + Zboard3Constant.PAGE_PER_PAGE;
		System.out.println("+===========d=fdas=f3fdsaf======");
		System.out.println("+===="+end);
		int next = end+1;
		
		if(end>=countOfPage) {
			end=countOfPage;
			next=0;
		}
		System.out.println("+===========d=fdas=f3fdsaf======");
		System.out.println(end);
		return new Page<T>(pageno, prev, start, end, next, null);
		
	}
}
