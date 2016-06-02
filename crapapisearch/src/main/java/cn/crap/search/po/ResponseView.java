package cn.crap.search.po;

import java.util.HashMap;
import java.util.Map;

import cn.crap.search.constants.ResponseCode;

public class ResponseView{
	private Integer code;
	private String message;
	private Map<String, Object> data;
	public ResponseView() {
		
	}
	
	public ResponseView(Integer code, Map<String, Object> map) {
		this.code = code;
		this.message = ResponseCode.getMessage(code);
		if(null != data){
			this.data = map;
		}
	}
	
	public void put(String key, Object object){
		if(this.data == null){
			this.data = new HashMap<String, Object>();
		}
		this.data.put(key, object);
	}
	public ResponseView(Integer code) {
		this.code = code;
		this.message = ResponseCode.getMessage(code);
	}
	
	public ResponseView(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
