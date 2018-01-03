package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;
import pojo.EasyUITreeNode;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper ItemCatMapper;

	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		// 1.根据parentId查询节点列表
		TbItemCatExample example = new TbItemCatExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 查询删除父节点是传入的节点的所有子节点
		List<TbItemCat> list = ItemCatMapper.selectByExample(example);
		//// 2、转换成EasyUITreeNode列表。
		List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node =new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//添加到列表
			resultList.add(node);
		}
           //返回
		return resultList;
	}

}
