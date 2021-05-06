package com.icia.zboard3.service.mvc;

import java.util.*;

import org.springframework.stereotype.*;

import com.icia.zboard3.dao.*;
import com.icia.zboard3.entity.*;

import lombok.*;
@RequiredArgsConstructor
@Service
public class ProductService {
	private final ProductDao productDao;
	
	public List<Product> list() {
		return productDao.findAll();
	}

	public List<Product> bottomsPage(List<Integer> cnos) {
		List<Product> product = productDao.findByBottoms(cnos);
		return product;
	}

	public List<Product> topsPage(List<Integer> cnos) {
		List<Product> product = productDao.findByTops(cnos);
		return product;
	}

	public List<Product> mainListPage() {
		List<Product> product = productDao.findAll();
		return product;
	}

}
