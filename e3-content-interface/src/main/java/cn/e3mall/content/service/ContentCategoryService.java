package cn.e3mall.content.service;

import java.util.List;

import pojo.EasyUITreeNode;
import utils.TaotaoResult;

public interface  ContentCategoryService {
	public List<EasyUITreeNode> getContentCategoryList(long parentId);
	public TaotaoResult addContentCategory(long parentId, String name);
	public TaotaoResult updateCategory(Long id, String name);
	public TaotaoResult deleteCategory(Long id);

}
