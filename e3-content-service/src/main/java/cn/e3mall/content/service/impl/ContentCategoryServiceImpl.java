package cn.e3mall.content.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import pojo.EasyUITreeNode;
import utils.TaotaoResult;
@Service
public class ContentCategoryServiceImpl  implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	public List<EasyUITreeNode> getContentCategoryList(long parentId){
		TbContentCategoryExample example =new  TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList =new ArrayList<EasyUITreeNode>();
		if(list !=null && list.size()>0){
			for (TbContentCategory tbContentCategory : list) {
				EasyUITreeNode node =new EasyUITreeNode();
				node.setId(tbContentCategory.getId());
				node.setState(tbContentCategory.getIsParent()?"closed":"open");
				node.setText(tbContentCategory.getName());
				resultList.add(node);
			}
		}
		return resultList;
	}
    //添加分类
	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		TbContentCategory contentCategory =new TbContentCategory();
		contentCategory.setIsParent(false);
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入数据
		contentCategoryMapper.insert(contentCategory);
		TbContentCategory parentNode  = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		
		return TaotaoResult.ok(contentCategory);
	}
	/**
	 * 修改类别
	 */
	@Override
	public TaotaoResult updateCategory(Long id, String name) {
		TbContentCategory contentCategory =new TbContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		int i = contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		 if(i>0){
			 return TaotaoResult.ok();
		 }
			return null;
	}
	/**
	 * 删除分类
	 */
	public TaotaoResult deleteCategory(Long id) {
		TaotaoResult result =new TaotaoResult();
		TbContentCategory cate = contentCategoryMapper.selectByPrimaryKey(id);
		//是父节点查询下面是否有子节点
		if(cate.getIsParent()){
		TbContentCategoryExample example =new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if(list !=null && list.size()>0){
			result.setStatus(-1);
			result.setMsg("该父节点有子节点不能删除");
			return result;
		}
		//父节点下没有子节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
		}
		
		return null;
	}
	

}
