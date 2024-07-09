package com.storage_app.service;

import java.util.Optional;

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
	
	public Optional<Item> selectById(Integer ItemId){
		return repos.findById(ItemId);
	}
	
	public Iterable<Item> selectChildrenById(Integer parentItemId){
		return repos.getChildItems(parentItemId);
	}
	
	//初期設定
	public void init() {
		//ルートノードの作成
		repos.insertRootItem();
	};
		
	//登録
	public Integer insertItem(Item item) {
		Integer childNo = repos.getMaxChildNo(item.getParentItemId()) + 1;
		item.setChildNo(childNo);
		
		Item insItem = repos.save(item);
		return insItem.getItemId();
	}
	
	//更新
	public void updateItem(Item item) {
		repos.save(item);
	}
	
	//削除
	public void deleteItemById(Integer ItemId) {
		repos.deleteById(ItemId);
	}
}
