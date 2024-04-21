package com.storage_app.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemForm {
	private String itemId;
	
	private String name;
	
	private String parentItemId;
	
	private Integer childNo;
	
	private String category;
	
	private Integer number;
	
	private String pictureId;
	
	private String note;
	
	private String tag1;
	
	private String tag2;
	
	private String tag3;
}
