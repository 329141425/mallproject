package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import pojo.EUDataGridResult;
import utils.TaotaoResult;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public  TbItem itemService(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList( @RequestParam(value="page",defaultValue="1") Integer page, @RequestParam(value="rows",defaultValue="10") Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult saveItem(TbItem item,String desc){
		TaotaoResult result = itemService.addItem(item, desc);
		return result;
	}
	

}
