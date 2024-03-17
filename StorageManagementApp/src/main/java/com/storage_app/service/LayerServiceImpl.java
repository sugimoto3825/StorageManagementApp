/*package com.storage_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.storage_app.LayerRepository;
import com.storage_app.entity.Layer;

@Service
@Transactional
public class LayerServiceImpl implements LayerService {
	@Autowired
	LayerRepository repos;
	
	public Iterable<Layer> selectAll(){
		return repos.findAll();
	}

}
*/