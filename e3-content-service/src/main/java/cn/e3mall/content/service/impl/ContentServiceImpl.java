package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import jedis.JedisClient;
import utils.JsonUtils;
import utils.TaotaoResult;
@Service
public class ContentServiceImpl  implements ContentService {
@Autowired
private TbContentMapper contentMapper;
@Autowired
private JedisClient jedisClient;
@Value("CONTENT_LIST")
private String CONTENT_LIST;
	@Override
	public TaotaoResult addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}
	/**
	 * 根据分类ID查询
	 */
	@Override
	public List<TbContent> getContentList(long cid) {
		//缓存中有的话先从缓存中拿
		try {
			String json = jedisClient.hget(CONTENT_LIST, cid+"");
			if(StringUtils.isNotBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
         TbContentExample example =new TbContentExample();
         Criteria criteria = example.createCriteria();
         criteria.andCategoryIdEqualTo(cid);
         //查询结果放入redis
         List<TbContent> list = contentMapper.selectByExample(example);
         try {
 			jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(list));
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
         return list;
	}

}