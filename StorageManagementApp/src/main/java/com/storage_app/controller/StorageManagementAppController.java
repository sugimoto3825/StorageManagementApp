package com.storage_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.storage_app.entity.Item;
import com.storage_app.entity.Json;
import com.storage_app.entity.Layer;
import com.storage_app.entity.LayerAndName;
import com.storage_app.service.ItemService;
import com.storage_app.service.LayerService;

@Controller
@RequestMapping("/storage_app")
public class StorageManagementAppController {
		@Autowired
		LayerService layerService;

		@Autowired
		ItemService itemService;
		
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
    public @ResponseBody List<Json> getTestTree(@RequestParam(name = "root") String root) {
//        return Arrays.asList(new Layer("001", "101", "orange"), new Layer("002", "102", "apple"));
//		return Arrays.asList(new Json("001", "orange", ""));
//		Json[] list = {new Json("002", "orange", null, false)};
//		return Arrays.asList(new Json("001", "fruit", list, false));
		
		List<Json> rtn = new ArrayList<Json>();
		
		if("source".equals(root)) {
			
			rtn = getChildren("0000000000");
			
			List<Json> children = new ArrayList<Json>();
			children = getChildren(rtn.get(0).getId());
			rtn.get(0).setChildren(children.toArray(new Json[children.size()]));
			for(int i = 0; i < children.size(); i++) {
				List<Json> grandChildren = new ArrayList<Json>();
				grandChildren = getChildren(rtn.get(0).getChildren()[i].getId());
				rtn.get(0).getChildren()[i].setChildren(grandChildren.toArray(new Json[grandChildren.size()]));
			}
				
			
		}
		else {
//			Integer id = Integer.parseInt(root);
			rtn = getChildren(root);
		}

		
//		rtn.add(json);
		return rtn;
    }
	
	private Json makeJsonByLAN(LayerAryAndName lan, Json[] children) {
//	private Json makeJson(LayerAndName lan, Json[] children) {
		Json json = new Json();
		
		json.setId(lan.getItemId());
		json.setText(lan.getItemName());
		json.setChildren(children);
		json.setHasChildren(false);
		if(children.length > 0) {
			json.setHasChildren(true);
		}
		
		return json;
	}
	
	private Json makeJsonByItem(Item item, Json[] children, Boolean expanded) {
//		private Json makeJson(LayerAndName lan, Json[] children) {
			Json json = new Json();
			
			json.setId(item.getItemId());
			json.setText(item.getName());
			json.setChildren(children);
			json.setHasChildren(false);
			/*			if(children.length > 0) {
							json.setHasChildren(true);
						}*/
			json.setExpanded(expanded);
			
			return json;
		}
	
	private  @ResponseBody List<Json> getChildren(String parentItemId) {
		List<Json> rtn = new ArrayList<Json>();
		Json[] children = {};
		
		Iterable<Item> list = itemService.selectChildrenById(parentItemId);
		
		for(Item i : list) {
			rtn.add(makeJsonByItem(i, children, false));
		}
		
		return rtn;
	}
	
	/* 階層情報の全データを表示する */
	private  @ResponseBody List<Json> showAll() {
		List<Json> rtn = new ArrayList<Json>();
		List<Json> children = new ArrayList<Json>();
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
	
	private void searchTree(ArrayList<LayerAryAndName> pAry, int pX, int pY, List<Json> pParent, List<Json> pChildren, boolean next) {
		pChildren = new ArrayList<Json>();
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
//			pChildren.add(makeJson(pAry.get(pX), pChildren.toArray(new Json[pChildren.size()])));
			return;
		}
	}
}
