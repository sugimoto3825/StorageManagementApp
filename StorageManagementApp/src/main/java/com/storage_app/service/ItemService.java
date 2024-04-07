package com.storage_app.service;

import com.storage_app.entity.Item;

public interface ItemService {

	Iterable<Item> selectAll();
	
	Iterable<Item> selectChildrenById(String parentItemId);

}
