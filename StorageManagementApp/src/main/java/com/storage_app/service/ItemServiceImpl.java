package com.storage_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.storage_app.ItemRepository;
import com.storage_app.entity.Item;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	ItemRepository repos;
	
	public Iterable<Item> selectAll(){
		return repos.findAll();
	}
	
	public Iterable<Item> selectChildrenById(String parentItemId){
		return repos.getChildItems(parentItemId);
	}
}
