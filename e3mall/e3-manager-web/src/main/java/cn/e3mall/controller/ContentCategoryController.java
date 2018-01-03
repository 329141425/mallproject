package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.content.service.ContentCategoryService;
import pojo.EasyUITreeNode;
import utils.TaotaoResult;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0")
	long parentId){
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createCategory(Long parentId,String name){
		TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateCategory(Long id,String name){
		TaotaoResult result =contentCategoryService.updateCategory(id,name);
		return result;
	}
	

}
