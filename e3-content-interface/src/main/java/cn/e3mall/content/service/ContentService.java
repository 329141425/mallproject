package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.pojo.TbContent;
import utils.TaotaoResult;

public interface ContentService {
	public TaotaoResult addContent(TbContent content);
	public List<TbContent>getContentList(long cid);

}
