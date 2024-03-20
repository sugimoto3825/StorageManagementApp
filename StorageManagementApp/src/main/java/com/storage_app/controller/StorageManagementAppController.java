package com.storage_app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.storage_app.entity.Json;

@Controller
@RequestMapping("/storage_app")
public class StorageManagementAppController {
	/*	@Autowired
		LayerService layerService;*/

	/*	@GetMapping
		public String showLayer(Model model) {
			//階層リストのセット
			Iterable<Layer> list;
			ArrayList<Layer> alist = new ArrayList<Layer>();
			Layer testLayer = new Layer();
			testLayer.setLayerId("100");
			testLayer.setItemId("001");
			alist.add(testLayer);
			Layer testLayer2 = new Layer();
			testLayer2.setLayerId("110");
			testLayer2.setItemId("002");
			alist.add(testLayer2);
			list = alist;
			
			model.addAttribute("list", list);
			
			return "crud";
		}
		*/
	@GetMapping("/tree")
    public @ResponseBody List<Json> getTestTree() {
//        return Arrays.asList(new Layer("001", "101", "orange"), new Layer("002", "102", "apple"));
//		return Arrays.asList(new Json("001", "orange", ""));
//		layerService.selectAll();
		Json[] list = {new Json("002", "orange", null, false)};
		return Arrays.asList(new Json("001", "fruit", list, false));
    }
}
