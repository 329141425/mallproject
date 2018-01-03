package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import utils.JsonUtils;

@Controller
public class PictureController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+ ";charset=UTF-8")
	@ResponseBody
	public String  uploadFile(MultipartFile  uploadFile,HttpServletRequest request) throws Exception{
		 String fileName = uploadFile.getOriginalFilename();
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		String accessKey = "c7yNpO-Nzv2DIHIqmP-WDk_sGxPH3_FrYmArex8I";
		String secretKey = "00q5MihyGatlWfhwvRELq8hBQMpPGqjfApzGBtMK";
		String bucket = "dsfsfsdf";
		//如果是Windows情况下，格式是 D:\\qiniu\\test.png
		//String localFilePath = uploadFile;
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		
		 byte[] uploadBytes = uploadFile.getBytes();
		
		Auth auth = Auth.create(accessKey, secretKey);
		DateTime dateTime = new DateTime();
		String filePath ="e3mallupload"+ dateTime.toString("/yyyy/MM/dd")+System.currentTimeMillis()+".jpg";
		String upToken = auth.uploadToken(bucket, null, 3600, new StringMap()
	            .putNotEmpty("saveKey", filePath), true);
		try {
			 Response response = uploadManager.put(uploadBytes, key, upToken);
			    //解析上传成功的结果
			 DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			 Map<String, Object> result =new HashMap<String, Object>();
			 String path=putRet.key;
			String url =IMAGE_SERVER_URL + path;
			 System.out.println(url);
			 result.put("error", 0);
			 result.put("url", url);
			 return JsonUtils.objectToJson(result);
		} catch (QiniuException ex) {
			Response r = ex.response;
			 Map<String, Object> result =new HashMap<String, Object>();
			 result.put("error", 1);
			 result.put("message", r.error);
			 return JsonUtils.objectToJson(result);
		}
		
	}

}
