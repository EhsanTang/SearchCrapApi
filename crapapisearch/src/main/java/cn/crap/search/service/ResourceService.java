package cn.crap.search.service;

import cn.crap.search.po.ResponseView;

public interface ResourceService {
	/**
	 * 
	 * @param data
	 * @return
	 */
	public ResponseView add(String data);
	
	/**
	 * @param ids json数组
	 * @return
	 */
	public ResponseView delete(String ids);
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public ResponseView update(String data);
	
	/**
	 * 
	 * @param param 查询参数
	 * @return
	 */
	public ResponseView  query(String param, Integer count, Integer start, Boolean hl, String sortfield, String sort, String ...fields);
}
