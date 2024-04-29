package com.storage_app.service;

import java.util.Optional;

import com.storage_app.entity.Item;

public interface ItemService {

	Iterable<Item> selectAll();
	
	Iterable<Item> selectChildrenById(Integer parentItemId);
	
	Optional<Item> selectById(Integer ItemId);

	//登録
	Integer insertItem(Item item);
	
	//更新
	void updateItem(Item item);
	
	//削除
	void deleteItemById(Integer ItemId);

}
