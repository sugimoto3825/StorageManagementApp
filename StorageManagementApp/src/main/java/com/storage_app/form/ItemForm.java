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
	
	@NotBlank
	@Size(max = 20)
	private String name;

	private Integer parentItemId;
	
	private Integer childNo;
	
	@Size(max = 20)
	private String category;
	
	@NotNull
	@Max(99999)
	@Min(0)
	private Integer number;
	
	private String pictureId;
	
	@Size(max = 100)
	private String note;
	
	@Size(max = 20)
	private String tag1;
	
	@Size(max = 20)
	private String tag2;
	
	@Size(max = 20)
	private String tag3;
}
