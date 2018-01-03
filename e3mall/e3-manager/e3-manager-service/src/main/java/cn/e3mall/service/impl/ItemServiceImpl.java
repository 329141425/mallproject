package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import pojo.EUDataGridResult;
import utils.IDUtils;
import utils.TaotaoResult;
/**
 * 商品管理service
 * @author dell
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
@Autowired
private TbItemMapper itemMapper;
@Autowired
private TbItemDescMapper itemDescMapper;
	@Override
	public TbItem getItemById(long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		return item;
	}
	
	public EUDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example =new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//创建返回对象
		EUDataGridResult result  =new EUDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		
		
		return result;
	}
	/**
	 * 添加商品
	 */
	public TaotaoResult addItem(TbItem item, String desc) {
		//生成id
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//插入数据
		itemMapper.insertSelective(item);
		TbItemDesc itemDesc =new TbItemDesc();
		// 5、补全TbItemDesc的属性
				itemDesc.setItemId(itemId);
				itemDesc.setItemDesc(desc);
				itemDesc.setCreated(date);
				itemDesc.setUpdated(date);
				// 6、向商品描述表插入数据
				itemDescMapper.insert(itemDesc);
				// 7、E3Result.ok()
				return TaotaoResult.ok();
	}

}
