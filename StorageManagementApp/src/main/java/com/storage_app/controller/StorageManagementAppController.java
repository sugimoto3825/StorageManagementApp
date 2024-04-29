package com.storage_app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.storage_app.entity.Layer;
import com.storage_app.entity.LayerAndName;
import com.storage_app.form.ItemForm;
import com.storage_app.service.ItemService;
import com.storage_app.service.LayerService;

@Controller
@RequestMapping("/storage_app")
public class StorageManagementAppController {
		@Autowired
		LayerService layerService;

		@Autowired
		ItemService itemService;
		
		static List<JsonNode> wholeLayer;
		
		@ModelAttribute
		public ItemForm setUpForm() {
			ItemForm form = new ItemForm();
			return form;
		}
		
		@GetMapping
		public String showLayer(Model model) {
			//階層リストのセット
			Iterable<Layer> list;
			ArrayList<Layer> alist = new ArrayList<Layer>();
			/*			Layer testLayer = new Layer();
						testLayer.setLayerId("100");
						testLayer.setItemId("001");
						alist.add(testLayer);
						Layer testLayer2 = new Layer();
						testLayer2.setLayerId("110");
						testLayer2.setItemId("002");
						alist.add(testLayer2);*/
			list = alist;
			
			model.addAttribute("list", list);
			
			return "crud";
		}
		
	@GetMapping("/tree")
    public @ResponseBody List<JsonNode> getTestTree(@RequestParam(name = "root") String root) {
//        return Arrays.asList(new Layer("001", "101", "orange"), new Layer("002", "102", "apple"));
//		return Arrays.asList(new Json("001", "orange", ""));
//		JsonNode[] list = {new Json("002", "orange", null, false)};
//		return Arrays.asList(new Json("001", "fruit", list, false));
		
		List<JsonNode> rtn = new ArrayList<JsonNode>();
		
		if("source".equals(root)) {
			
			rtn = getChildren(0);
			
			List<JsonNode> children = new ArrayList<JsonNode>();
			children = getChildren(rtn.get(0).getId());
			rtn.get(0).setChildren(children.toArray(new JsonNode[children.size()]));
			for(int i = 0; i < children.size(); i++) {
				List<JsonNode> grandChildren = new ArrayList<JsonNode>();
				grandChildren = getChildren(rtn.get(0).getChildren()[i].getId());
				rtn.get(0).getChildren()[i].setChildren(grandChildren.toArray(new JsonNode[grandChildren.size()]));
			}
				
			
		}
		else {
			Integer id = Integer.parseInt(root);
			rtn = getChildren(id);
		}

		
//		rtn.add(json);
		return rtn;
    }
	
	private JsonNode makeJsonNodeByItem(Item item, JsonNode[] children, Boolean expanded) {
//		private Json makeJson(LayerAndName lan, JsonNode[] children) {
		JsonNode json = new JsonNode();
			
			json.setId(item.getItemId());
			json.setText(item.getName());
			json.setText("<a href=\"javascript:getItem(" + item.getItemId() + ");\">" + item.getName() + "</a>");
			json.setChildren(children);
//			json.setHasChildren(true);
			if(children.length > 0) {
				json.setHasChildren(true);
			}
			json.setExpanded(expanded);
			
			return json;
		}
	
	private  @ResponseBody List<JsonNode> getChildren(Integer parentItemId) {
		List<JsonNode> rtn = new ArrayList<JsonNode>();
		JsonNode[] children = {};
		
		Iterable<Item> list = itemService.selectChildrenById(parentItemId);
		
		for(Item i : list) {
			rtn.add(makeJsonNodeByItem(i, children, false));
		}
		
		return rtn;
	}
	
	/* 階層情報の全データを表示する */
	private  @ResponseBody List<JsonNode> showAll() {
		List<JsonNode> rtn = new ArrayList<JsonNode>();
		List<JsonNode> children = new ArrayList<JsonNode>();
		ArrayList<LayerAryAndName> alist = new ArrayList<LayerAryAndName>();
		
		Iterable<LayerAndName> list = layerService.selectAll();
		
		for(LayerAndName l : list){
			LayerAryAndName tmp = new LayerAryAndName();
			
			tmp.setItemId(l.getItemId());
			tmp.setItemName(l.getItemName());
			Integer[] layers = {l.getLayerId1(), l.getLayerId2(), l.getLayerId3(), l.getLayerId4(), l.getLayerId5()};
			tmp.setLayerId(layers);
			alist.add(tmp);
		}
		
		searchTree(alist, 0, 0, rtn, children, false);
		
		return rtn;
	}
	
	private void searchTree(ArrayList<LayerAryAndName> pAry, int pX, int pY, List<JsonNode> pParent, List<JsonNode> pChildren, boolean next) {
		pChildren = new ArrayList<JsonNode>();
		boolean existNextChild = true;
		boolean existNextLayer = true;
		
		int i = 0;
		for(; i < pY; i++) {
			if(pAry.get(pX).getLayerId()[i] != pAry.get(pX + 1).getLayerId()[i]) {
				//次レコードの親階層が違う
				existNextChild = false;
				existNextLayer = false;
				break;
			}
		}
		if(pAry.get(pX).getLayerId()[i] != pAry.get(pX + 1).getLayerId()[i]) {
			existNextLayer = false;
		}
		
		
		if(existNextLayer) {
			//次の階層の子すべてを探索する（子がいなくなったらexistNextChild = false）
			do {
				pX += 1;
				pY += 1;
				searchTree(pAry, pX, pY, pParent, pChildren, existNextChild);
			} while(existNextChild);
		}
		else {
//			pChildren.add(makeJson(pAry.get(pX), pChildren.toArray(new JsonNode[pChildren.size()])));
			return;
		}
	}
	
//	@GetMapping("/{id}")
//    public String getItem(ItemForm itemForm, @PathVariable String id, Model model) {
//		Optional<Item> itemOpt = itemService.selectById(id);
//		Optional<ItemForm> itemFormOpt = itemOpt.map(t -> makeItemForm(t));
//		itemForm = itemFormOpt.orElse(null);
//		model.addAttribute("itemForm", itemForm);
//		
//		return "crud";
//    }
	
	@GetMapping("/{id}")
	@ResponseBody
	public ItemForm getItem(ItemForm itemForm, @PathVariable Integer id, Model model) {
		Optional<Item> itemOpt = itemService.selectById(id);
		return makeItemForm(itemOpt.get());
	}
	
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public @ResponseBody void getItem(ItemForm itemForm, @PathVariable String id, Model model) {
//		Optional<Item> itemOpt = itemService.selectById(id);
//		Optional<ItemForm> itemFormOpt = itemOpt.map(t -> makeItemForm(t));
//		itemForm = itemFormOpt.orElse(null);
//		model.addAttribute("itemForm", itemForm);
//    }
//	
	
	@PostMapping("/insert")
	@ResponseBody
	public Integer insertItem(
			@Validated ItemForm itemForm
			,BindingResult result
			) {
		Item item = new Item();
		Integer itemId = null;
		
		if(result.hasErrors() == false) {
			item = makeItem(itemForm);
			
			itemId = itemService.insertItem(item);
			//itemService.insertItem(item);
		}
		return itemId;
		//return "crud";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Integer updateItem(
			@Validated ItemForm itemForm
			,BindingResult result
			) {
		Item item = new Item();
		Integer itemId = null;
		
		if(result.hasErrors() == false) {
			item = makeItem(itemForm);
			itemId = item.getItemId();
			
			itemService.updateItem(item);
		}
		return itemId;
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
