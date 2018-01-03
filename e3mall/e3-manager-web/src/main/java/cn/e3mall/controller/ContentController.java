package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import utils.TaotaoResult;

@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content){
		TaotaoResult result = contentService.addContent(content);
		return result;
	}

}
