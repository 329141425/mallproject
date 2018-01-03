package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;
import pojo.EUDataGridResult;
import utils.TaotaoResult;

public interface ItemService {
	TbItem getItemById(long id);
	//分页查询商品列表
	public EUDataGridResult getItemList(int page, int rows);
	//添加商品
	TaotaoResult addItem(TbItem item,String desc);

}
