package com.storage_app.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonNode {
	@Id
	Integer id;
	String text;
	JsonNode[] children;
	Boolean hasChildren;
	Boolean expanded;
}
