package com.storage_app;

import org.springframework.data.repository.CrudRepository;

import com.storage_app.entity.Layer;

public interface LayerRepository extends CrudRepository<Layer, String> {
}
