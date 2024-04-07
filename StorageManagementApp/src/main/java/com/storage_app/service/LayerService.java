package com.storage_app.service;

import com.storage_app.entity.LayerAndName;

public interface LayerService {

	Iterable<LayerAndName> selectAll();

}
