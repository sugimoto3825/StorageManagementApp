package com.storage_app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	@Id
	@Column("item_id")
	private String itemId;
	
	private String name;
	
	private String category;
	
	private Integer number;
	
	@Column("picture_id")
	private String pictureId;
	
	private String note;
	
	private String tag1;
	
	private String tag2;
	
	private String tag3;

}
