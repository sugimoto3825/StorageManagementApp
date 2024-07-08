package com.storage_app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.storage_app.entity.Item;
import com.storage_app.entity.JsonNode;
import com.storage_app.form.GroupOrder;
import com.storage_app.form.ItemForm;
import com.storage_app.service.ItemService;

@Controller
@RequestMapping("/storage_app")
public class StorageManagementAppController {
	@Autowired
	ItemService itemService;
	
	static List<JsonNode> wholeLayer;
	
	@Autowired
	MessageSource messages;
	
	@ModelAttribute
	public ItemForm setUpForm() {
		ItemForm form = new ItemForm();
		return form;
	}
		
	@GetMapping
	public String displayInitPage() {
		return "crud";
	}
	
	@GetMapping("/tree")
    public @ResponseBody List<JsonNode> getTree(@RequestParam(name = "root") String root) {
		List<JsonNode> rtn = new ArrayList<JsonNode>();
		
		if("source".equals(root)) {
			//ルートからすべてのノードを取得する
			rtn = getDescendants(0);
		}
		else {
			//指定したIDの子孫ノードを取得する（※現在この分岐は通らない）
			Integer id = Integer.parseInt(root);
			rtn = getDescendants(id);
		}

		return rtn;
    }
	
	private JsonNode makeJsonNodeByItem(Item item, JsonNode[] children, Boolean expanded) {
		JsonNode json = new JsonNode();
			
			json.setId(item.getItemId());
			json.setText(item.getName());
			json.setText("<a href=\"javascript:getItem(" + item.getItemId() + ");\">" + item.getName() + "</a>");
			json.setChildren(children);
			//HasChildren = Trueにすると読込中アイコンが表示されるため常にFalseにする
			json.setHasChildren(false);
			/*
			if(children.length > 0) {
				json.setHasChildren(true);
			}
			*/
			json.setExpanded(expanded);
			
			return json;
		}
	
	private  @ResponseBody List<JsonNode> getDescendants(Integer parentItemId) {
		List<JsonNode> rtn = new ArrayList<JsonNode>();
		
		//子ノードを取得する
		Iterable<Item> list = itemService.selectChildrenById(parentItemId);
		
		for(Item item : list) {
			List<JsonNode> children = new ArrayList<JsonNode>();
			
			//孫ノードを取得する
			children = getDescendants(item.getItemId());
			JsonNode[] childrenArray = children.toArray(new JsonNode[children.size()]);
			rtn.add(makeJsonNodeByItem(item, childrenArray, false));
		}
		
		return rtn;
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ItemForm getItem(ItemForm itemForm, @PathVariable Integer id, Model model) {
		Optional<Item> itemOpt = itemService.selectById(id);
		return makeItemForm(itemOpt.get());
	}
	
	@PostMapping("/insert")
	@ResponseBody
	public List<String> insertItem(
			@Validated(GroupOrder.class) ItemForm itemForm
			,BindingResult result
			) {
		Item item = new Item();
		
		List<String> errorMessages = new ArrayList<String>();
		
		if(result.hasErrors() == false) {
			item = makeItem(itemForm);
			
			itemService.insertItem(item);
		}
		else {
			//エラーメッセージの取得
	        for (ObjectError error : result.getAllErrors()) {
	        	errorMessages.add(messages.getMessage(error, Locale.JAPAN));
	        }
		}
		return errorMessages;
	}
	
	@PostMapping("/update")
	@ResponseBody
		public List<String>  updateItem(
			@Validated(GroupOrder.class) ItemForm itemForm
			,BindingResult result
			) {
		Item item = new Item();
		
		List<String> errorMessages = new ArrayList<String>();
		
		if(result.hasErrors() == false) {
			item = makeItem(itemForm);
			
			itemService.updateItem(item);
		}
		else {
			//エラーメッセージの取得
	        for (ObjectError error : result.getAllErrors()) {
	        	errorMessages.add(messages.getMessage(error, Locale.JAPAN));
	        }
		}
		return errorMessages;
	}

	
	@PostMapping("/delete")
	@ResponseBody
	public Integer deleteItem(
			@RequestParam String itemId
			) {
		itemService.deleteItemById(Integer.parseInt(itemId));
		return null;
	}
	
	private ItemForm makeItemForm(Item item){
		ItemForm itemForm = new ItemForm();
		itemForm.setItemId(item.getItemId());
		itemForm.setName(item.getName());
		itemForm.setParentItemId(item.getParentItemId());
		itemForm.setChildNo(item.getChildNo());
		itemForm.setCategory(item.getCategory());
		itemForm.setNumber(item.getNumber());
		itemForm.setPictureId(item.getPictureId());
		itemForm.setNote(item.getNote());
		itemForm.setTag1(item.getTag1());
		itemForm.setTag2(item.getTag2());
		itemForm.setTag3(item.getTag3());

		return itemForm;
	}
	
	private Item makeItem(ItemForm itemForm) {
		Item item = new Item();
		item.setItemId(itemForm.getItemId());
		item.setName(itemForm.getName());
		item.setParentItemId(itemForm.getParentItemId());
		item.setChildNo(itemForm.getChildNo());
		item.setCategory(itemForm.getCategory());
		item.setNumber(itemForm.getNumber());
		item.setPictureId(itemForm.getPictureId());
		item.setNote(itemForm.getNote());
		item.setTag1(itemForm.getTag1());
		item.setTag2(itemForm.getTag2());
		item.setTag3(itemForm.getTag3());
		return item;
	}
	
}
