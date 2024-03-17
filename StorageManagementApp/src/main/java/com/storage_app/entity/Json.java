package com.storage_app.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Json {
	@Id
	String id;
	String text;
	Json[] children;
	Boolean hasChildren;
}
