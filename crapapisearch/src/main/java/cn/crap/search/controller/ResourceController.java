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
	 * @param data 必须
	 * [{"id":"1","url":"http://yanxiaowei.com","title":"this is title", "content":"this is the article content"},{"id":"2","url":"http://yanshare.com","title":"this is the other title", "content":"this is the other article content"}]
	 * data参数为json数组，要求在增加索引前，需要通过solr管理添加所有属性的schema,如 id url title content
	 * 需要注意的是如果id重复，则会覆盖上一条信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="add")
	public ResponseView index_add( String data){
		return resourceService.add(data);
	}
	
	/**
	 * 删除索引
	 * @param ids 索引数组 必须
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
	 * @param data 必须
	 * [{"id":"1","url":"http://yanxiaowei.com","title":"this is title", "content":"this is the article content"},{"id":"2","url":"http://yanshare.com","title":"this is the other title", "content":"this is the other article content"}]
	 * 更新索引的原理为先删除旧的索引，然后创建新索引
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="update")
	public ResponseView index_update(String data){
		return resourceService.update(data);
	}
	
	/**
	 * 查询索引
	 * @param param 查询关键字 必须
	 * @param count 最多返回的结果数 非必须
	 * @param start 从第几条查起 非必须
	 * @param hl 是否高亮 非必须
	 * @param sortfield 排序字段 非必须
	 * @param sort 排序规则（asc or desc） 非必须
	 * @param fields 返回结果显示字段 (fields=id&fields=title) 非必须
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, value="query")
	public ResponseView index_query(String param, Integer count, Integer start, Boolean hl, String sortfield, String sort, String ...fields){
		return resourceService.query(param, count, start, hl, sortfield, sort, fields);
	}
	
}
