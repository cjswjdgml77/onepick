package com.icia.zboard3.dto;

import java.util.*;

import javax.servlet.http.*;

import com.icia.zboard3.entity.*;
import com.icia.zboard3.exception.*;

import lombok.*;

@Data
public class Cart{
	private List<CartItem> cartItemList;
	
	private Integer totalPrice;
	
	//전체 가격 재 계산
	private void calc() {
		this.totalPrice=0;
		for(CartItem cartItem:cartItemList)
			this.totalPrice = this.totalPrice + cartItem.getItemPrice();
	}
	
	// 장바구니가 없는 상태에서 상품 추가 -> 장바구니를 생성하고 cartItemList에 상품을 추가
	public Cart(CartItem cartItem,Product product) {
		if(product.getStock()<0)
			throw new ShopJob.OutOfStockException();
		this.cartItemList = new ArrayList<>();
		cartItemList.add(cartItem);
		this.totalPrice = cartItem.getItemPrice();
	}
	// 상품 추가. 상품이 있으면 개수 증가, 없으면 상품 추가
	public void addCart(CartItem cartItem) {
		Boolean isExist =false;
		for(CartItem item:cartItemList) {
			if(item.getPno()==cartItem.getPno()) {
				item.increase();
				isExist=true;
			}
		}
		if(isExist==false) 
			cartItemList.add(cartItem);
		calc();
		
	}
	// 개수 증가
	public CartItem increase(Integer pno, Product product) {
		for(CartItem item:cartItemList) {
			if(item.getPno()==pno) {
				if(product.getStock()>=item.getCount()+1) {
					item.increase();
					calc();
					return item;
				}
			}
		}
		return null;
	}
	// 개수 감소
	public CartItem decrease(Integer pno) {
		for(CartItem item:cartItemList) {
			if(item.getPno()==pno) {
				if(item.getCount()>=2) {
					item.decrease();
					calc();
					return item;
				}
				return item;
			}
		}
		return null;
	}
	// 상품 삭제
	public Cart delete(List<Integer> pnos) {
		for(Integer p:pnos) {
			for(int idx=cartItemList.size()-1; idx>=0; idx--) {
					if(cartItemList.get(idx).getPno()==p) {
						cartItemList.remove(idx);
					}
			}
		}
		calc();
		return this;
	}
	
}
