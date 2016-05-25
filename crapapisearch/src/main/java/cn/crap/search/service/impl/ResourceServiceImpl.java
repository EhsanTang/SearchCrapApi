package cn.crap.search.service.impl;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.StringUtils;
import org.springframework.stereotype.Service;

import cn.crap.search.constants.ResponseCode;
import cn.crap.search.po.ResponseView;
import cn.crap.search.service.ResourceService;
import cn.crap.search.utils.SolrClientKit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
@Service
public class ResourceServiceImpl implements ResourceService{
	static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	
	public ResponseView add(String data) {
		List<Map<String, Object>> datas = null;
		try {
			datas = gson.fromJson(data, new TypeToken<List<Map<String, Object>>>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(datas ==  null || datas.size() == 0){
			return new ResponseView(ResponseCode.DATA_EMPTY);
		}
		int result = SolrClientKit.add(datas);
		return new ResponseView(ResponseCode.SUCCESS, "success index " + result + " data");
	}

	
	public ResponseView delete(String ids) {
		List<String> idlist = null;
		try {
			idlist = gson.fromJson(ids, new TypeToken<List<String>>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(idlist ==  null || idlist.size() == 0){
			return new ResponseView(ResponseCode.IDS_EMPTY);
		}
		int result = SolrClientKit.delete(idlist);
		return new ResponseView(ResponseCode.SUCCESS, "success delete " + result + " index");
	}

	public ResponseView update(String data) {
		List<Map<String, Object>> datas = null;
		try {
			datas = gson.fromJson(data, new TypeToken<List<Map<String, Object>>>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(datas ==  null || datas.size() == 0){
			return new ResponseView(ResponseCode.DATA_EMPTY);
		}
		int result = SolrClientKit.update(datas);
		return new ResponseView(ResponseCode.SUCCESS, "success update " + result + " index");
	}

	public ResponseView query(String param) {
		if(StringUtils.isEmpty(param)){
			return new ResponseView(ResponseCode.PARAM_EMPTY);
		}
		SolrDocumentList result = SolrClientKit.query(param);
		if(result == null){
			return new ResponseView(ResponseCode.QUERY_ERROR);
		}
		ResponseView rv = new ResponseView(ResponseCode.SUCCESS);
		rv.put("result", result);
		return rv;
	}

}
