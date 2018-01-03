package cn.e3mall.service;

import java.util.List;

import pojo.EasyUITreeNode;

public interface ItemCatService {
	public List<EasyUITreeNode> getCatList(long parentId);

}
