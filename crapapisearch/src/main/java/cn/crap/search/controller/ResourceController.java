package cn.crap.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.search.po.ResponseView;
import cn.crap.search.service.ResourceService;

@Controller
@RequestMapping("/api")
public class ResourceController {
	@Autowired
	ResourceService resourceService;
	/**
	 * 批量添加索引
	 * @param data 
	 * [{"id":"1","title":"这个是标题"},{"id":"2","title":"这个是标题2"}]
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="add")
	public ResponseView index_add( String data){
		return resourceService.add(data);
	}
	
	/**
	 * 删除索引
	 * @param ids
	 * ["1","2"]
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="delete")
	public ResponseView index_delete(String ids){
		return resourceService.delete(ids);
	}
	
	/**
	 * 更新索引
	 * @param data
	 * [{"id":"1","title":"这个是标题update"},{"id":"2","title":"这个是标题2update"}]
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="update")
	public ResponseView index_update(String data){
		return resourceService.update(data);
	}
	
	/**
	 * 查询索引
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="query")
	public ResponseView index_query(String param){
		return resourceService.query(param);
	}
	
}
