package com.storage_app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Layer {
	@Id
	@Column("layer_id")
	private String layerId;
	
	@Column("item_id")
	private String itemId;
}
