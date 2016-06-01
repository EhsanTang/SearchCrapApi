package cn.crap.search.service.impl;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
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
	static Logger logger = Logger.getLogger(ResourceServiceImpl.class);
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

	public ResponseView query(String param, Integer count, Integer start, Boolean hl,String sortfield, String sort, String ...fields) {
		if(StringUtils.isEmpty(param)){
			return new ResponseView(ResponseCode.PARAM_EMPTY);
		}
		QueryResponse res = SolrClientKit.query(param, count, start, hl,sortfield, sort, fields);
		if(res == null){
			logger.debug("query error. query response is null.");
			return new ResponseView(ResponseCode.QUERY_ERROR);
		}
		if(res.getStatus() != 0 ){
			logger.debug("query client error. error code is " +  res.getStatus());
			return new ResponseView(ResponseCode.QUERY_CLIENT_ERROR);
		}
		ResponseView rv = new ResponseView(ResponseCode.SUCCESS);
		rv.put("response", res.getResults());
		rv.put("start", res.getResults().getStart());
		rv.put("elapsedtime", res.getElapsedTime());
		rv.put("numfound", res.getResults().getNumFound());
		if(hl != null && hl){
			rv.put("highlighting", res.getHighlighting());
		}
		return rv;
	}

}
