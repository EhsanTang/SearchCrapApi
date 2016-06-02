package com.yan.passsafe.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;
import cn.crap.search.utils.ConfKit;

public class AuthFilter implements Filter {
	private final static String AUTHERUSER = ConfKit.get("api.username") + ":" + ConfKit.get("api.password");
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String sessionAuth = (String) request.getSession().getAttribute("auth");  
        if (sessionAuth != null) {  
        	filterChain.doFilter(request, response);
        	return;
        } 
        
        if(!checkHeaderAuth(request, response)){  
            response.setStatus(401);  
            response.setHeader("Cache-Control", "no-store");  
            response.setDateHeader("Expires", 0);  
            response.setHeader("WWW-authenticate", "Basic Realm=\"Authorization\"");  
            return;
        }
        filterChain.doFilter(request, response);
  
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
	
	private boolean checkHeaderAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        String auth = request.getHeader("Authorization");  
        if ((auth != null) && (auth.length() > 6)) {  
            auth = auth.substring(6, auth.length());  
            String decodedAuth = getFromBASE64(auth);  
            if(decodedAuth!=null && decodedAuth.equals(AUTHERUSER)){
            	request.getSession().setAttribute("auth", decodedAuth);  
            	return true;
            }
        }
        return false;  
  
    }  
  
    private String getFromBASE64(String s) {  
        if (s == null)  
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try {  
            byte[] b = decoder.decodeBuffer(s);  
            return new String(b);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
}
