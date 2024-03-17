package com.storage_app.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Layer {
	@Id
	@Column(name = "layer_id")
	private String layerId;
	
	@Column(name = "item_id")
	private String itemId;
}
