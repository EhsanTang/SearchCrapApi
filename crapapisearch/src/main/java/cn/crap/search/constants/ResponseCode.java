package cn.crap.search.constants;

import java.util.HashMap;
import java.util.Map;

import cn.crap.search.po.ResponseView;

public class ResponseCode {
	private static Map<Integer, String> status = new HashMap<Integer, String>();
	public static Integer SUCCESS = 0;
	public static Integer ERROR = -1;
	public static Integer DATA_EMPTY = 1000;
	public static Integer PARAM_EMPTY = 1001;
	public static Integer IDS_EMPTY = 1002;
	public static Integer QUERY_ERROR = 1003;
	public static Integer QUERY_CLIENT_ERROR = 1004;
	static{
		status.put(SUCCESS, "success");
		status.put(ERROR, "error");
		status.put(DATA_EMPTY, "index data can not be empty");
		status.put(PARAM_EMPTY, "query can not be empty");
		status.put(IDS_EMPTY, "delete index id can not be empty");
		status.put(QUERY_ERROR, "query error");
		status.put(QUERY_CLIENT_ERROR, "query client error");
	}
	
	/**
	 * 错误信息
     * @param code
     * @return
     */
    public static String getMessage(int code){
        if(status.containsKey(code)){
            return status.get(code);
        }
        return "【"+code+"】未知错误!";
    }
	
    public static String getSuccessMessage(){
    	return getJsonMessage(SUCCESS);
    }
    
	public static String getJsonMessage(int code, String out_code){
    	if(status.containsKey(code)){
    		return "{\"code\":"+code+",\"msg\":\""+ResponseCode.getMessage(code)+"\",\"out_code\":\""+out_code+"\"}";
    	}
    	return "{\"code\":"+code+",\"msg\":\"未知错误\",\"out_code\":"+out_code+"}"; 
    }
    
    public static String getJsonMessage(int code){
    	if(status.containsKey(code)){
    		return "{\"code\":"+code+",\"msg\":\""+ResponseCode.getMessage(code)+"\"}";
    	}
    	return "{\"code\":"+code+",\"msg\":\"未知错误\"}"; 
    }
    
    public static ResponseView getResponseView(Integer code){
    	return new ResponseView(code);
    }
    
    public static ResponseView getResponseViewSuccess(){
    	return new ResponseView(SUCCESS);
    }
}
