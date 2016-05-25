package cn.crap.search.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 * @author yan
 */
public class SolrClientKit {
	static Logger logger = Logger.getLogger(SolrClientKit.class);
	static String pro_url = "solr.url";
	static String queueSize = "solr.queueSize";
	static String threadCount = "solr.threadCount";
	private static SolrClient httpSolrClinet  = null;
	private static SolrClient concurrentUpdateSolrClient = null;
	
	/**
	 * 查询时优先使用此对象
	 * @return
	 */
	public static SolrClient getHttpSolrCientInstance(){
		if(httpSolrClinet == null){
			return new HttpSolrClient(ConfKit.get(pro_url));
		}
		return httpSolrClinet;
	}
	
	/**
	 * 批量更新时，优先使用此对象
	 * Although any SolrClient request can be made with this implementation,
	 * it is only recommended to use the ConcurrentUpdateSolrClient for update requests.
	 * @return
	 */
	public static SolrClient getConcurrentUpdateSolrClientInstance(){
		if(concurrentUpdateSolrClient == null){
			return new ConcurrentUpdateSolrClient(ConfKit.get(pro_url), Integer.parseInt(ConfKit.get(queueSize)), Integer.parseInt(ConfKit.get(threadCount)));
		}
		return concurrentUpdateSolrClient;
	}
	
	/**
	 * 批量添加索引数据
	 * @param documentlist
	 * @return
	 */
	public static int add(List<Map<String, Object>> documentlist){
		if(documentlist == null || documentlist.size() == 0){
			logger.debug("input list size: 0");
			return 0;
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (Map<String, Object> map : documentlist) {
			SolrInputDocument document = new SolrInputDocument();
			Set<String> keySets = map.keySet();
			for (String key : keySets) {
				document.addField(key, map.get(key));
			}
			docs.add(document);
		}
		try {
			getHttpSolrCientInstance().add(docs);
			getHttpSolrCientInstance().optimize();
			UpdateResponse res = getHttpSolrCientInstance().commit();
			if(res.getStatus() == 0){
				return docs.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 查询索引
	 * @param queryParam
	 * @return
	 */
	public static SolrDocumentList query(String queryParam){
		SolrClient client = getHttpSolrCientInstance();
		SolrQuery query = new SolrQuery();
		query.set("wt", "json");
		query.setQuery(queryParam);
		try {
			QueryResponse resp = client.query(query);
			SolrDocumentList docList = resp.getResults();
			client.close();
			return docList;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 更新索引，更新策略为先删除原来索引，然后再重新加入需要更新的索引
	 */
	public static int update(List<Map<String, Object>> documentlist){
		SolrClient client = getConcurrentUpdateSolrClientInstance();
		if(documentlist == null || documentlist.size() == 0){
			logger.debug("input list size: 0");
			return 0;
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		List<String> ids = new ArrayList<String>();
 		for (Map<String, Object> map : documentlist) {
			SolrInputDocument document = new SolrInputDocument();
			Set<String> keySets = map.keySet();
			for (String key : keySets) {
				document.addField(key, map.get(key));
				if(key.equals("id")){
					docs.add(document);
				}
			}
			if(keySets.contains("id")){
				ids.add(String.valueOf(map.get("id")));
			}else{
				logger.warn("no primary key");
			}
		}
		try {
			client.deleteById(ids);
			client.commit();
			client.add(docs);
			client.optimize();
			UpdateResponse res = client.commit();
			client.close();
			if(res.getStatus() == 0){
				return docs.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除索引
	 * @param ids
	 * @return
	 */
	public static int delete(List<String> ids){
		SolrClient client = getHttpSolrCientInstance();
		try {
			//先delete需要更新的id
			client.deleteById(ids);
			UpdateResponse res = client.commit();
			client.close();
			if(res.getStatus() == 0){
				return ids.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
