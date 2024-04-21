package com.storage_app.service;

import java.util.Optional;

import com.storage_app.entity.Item;

public interface ItemService {

	Iterable<Item> selectAll();
	
	Iterable<Item> selectChildrenById(String parentItemId);
	
	Optional<Item> selectById(String ItemId);

}
