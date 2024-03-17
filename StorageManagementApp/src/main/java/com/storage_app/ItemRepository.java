package com.storage_app;

import org.springframework.data.repository.CrudRepository;

import com.storage_app.entity.Item;

public interface ItemRepository extends CrudRepository<Item, String> {
	
}
