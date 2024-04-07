package com.storage_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.storage_app.LayerRepository;
import com.storage_app.entity.LayerAndName;

@Service
@Transactional
public class LayerServiceImpl implements LayerService {
	@Autowired
	LayerRepository repos;
	
	public Iterable<LayerAndName> selectAll(){
		return repos.getLayerAndName();
	}
	
	public Iterable<LayerAndName> selectChildren(){
		return repos.getLayerAndName();
	}
	
	public Iterable<LayerAndName> selectRoot(){
		return repos.getLayerAndName();
	}

}
