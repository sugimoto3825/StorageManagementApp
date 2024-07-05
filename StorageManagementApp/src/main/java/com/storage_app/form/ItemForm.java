package com.storage_app.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemForm {
	private Integer itemId;
	
	@NotBlank(groups = ValidationGroup1.class)
	@Size(max = 20, groups = ValidationGroup2.class)
	private String name;

	private Integer parentItemId;
	
	private Integer childNo;
	
	@Size(max = 20, groups = ValidationGroup1.class)
	private String category;
	
	@NotNull(groups = ValidationGroup1.class)
	@Max(value = 99999, groups = ValidationGroup2.class)
	@Min(value = 0, groups = ValidationGroup2.class)
	private Integer number;
	
	private String pictureId;
	
	@Size(max = 400, groups = ValidationGroup1.class)
	private String note;
	
	@Size(max = 20, groups = ValidationGroup1.class)
	private String tag1;
	
	@Size(max = 20, groups = ValidationGroup1.class)
	private String tag2;
	
	@Size(max = 20, groups = ValidationGroup1.class)
	private String tag3;
}
