package com.storage_app;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.storage_app.entity.Layer;
import com.storage_app.entity.LayerAndName;

public interface LayerRepository extends CrudRepository<Layer, String> {
	@Query(" select LAYER.*, ITEM.NAME from LAYER"
			+ " inner join ITEM on LAYER.ITEM_ID = ITEM.ITEM_ID"
			+ " order by LAYER_ID1, LAYER_ID2, LAYER_ID3, LAYER_ID4, LAYER_ID5")
	Iterable<LayerAndName> getLayerAndName();
}
