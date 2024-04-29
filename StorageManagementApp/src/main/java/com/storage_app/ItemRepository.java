package com.storage_app;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.storage_app.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {
	@Query(" select * from ITEM"
			+ " where P_ITEM_ID = :parentItemId"
			+ " order by C_NO")
	Iterable<Item> getChildItems(@Param("parentItemId") Integer parentItemId);
	
	@Query(" select coalesce(max(C_NO), 0) from ITEM"
			+ " where P_ITEM_ID = :parentItemId"
			+ " order by C_NO")
	Integer getMaxChildNo(@Param("parentItemId") Integer parentItemId);
	
	
}
