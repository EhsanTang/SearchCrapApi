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
	 * @param param
	 * @return
	 */
	public ResponseView  query(String param);
}
