package com.storage_app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LayerAndName {
	@Column("layer_id1")
	private Integer layerId1;

	@Column("layer_id2")
	private Integer layerId2;
	
	@Column("layer_id3")
	private Integer layerId3;
	
	@Column("layer_id4")
	private Integer layerId4;
	
	@Column("layer_id5")
	private Integer layerId5;
	
	@Id
	@Column("item_id")
	private String itemId;
	
	@Column("name")
	private String itemName;
}
