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
	@Column("ITEM_ID")
	private Integer itemId;
	
	private String name;
	
	@Column("P_ITEM_ID")
	private Integer parentItemId;
	
	@Column("C_NO")
	private Integer childNo;
	
	private String category;
	
	private Integer number;
	
	@Column("PICTURE_ID")
	private String pictureId;
	
	private String note;
	
	private String tag1;
	
	private String tag2;
	
	private String tag3;

}
