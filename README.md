# SearchCrapApi - CrapApi子项目

>此项目用于支持crapapi的全文检索

solr下载地址
http://www.apache.org/dyn/closer.lua/lucene/solr/6.0.1

###新增索引
请求地址 /api/add
param data 必须
data参数格式 [{"id":"1","url":"http://yanxiaowei.com","title":"this is title", "content":"this is the article content"},{"id":"2","url":"http://yanshare.com","title":"this is the other title", "content":"this is the other article content"}]
data参数为json数组，要求在增加索引前，需要通过solr管理添加所有属性的schema,如 id url title content
需要注意的是如果id重复，则会覆盖上一条信息

###删除索引
param ids 索引数组 必须
参数格式 ["1","2"]

###修改索引
param data 必须
data参数格式 [{"id":"1","url":"http://yanxiaowei.com","title":"this is title", "content":"this is the article content"},{"id":"2","url":"http://yanshare.com","title":"this is the other title", "content":"this is the other article content"}]
data参数为json数组，要求在增加索引前，需要通过solr管理添加所有属性的schema,如 id url title content
更新索引的原理为先删除旧的索引，然后创建新索引

###查询索引
@param param 查询关键字 必须
@param count 最多返回的结果数 非必须
@param start 从第几条查起 非必须
@param hl 是否高亮 非必须
@param sortfield 排序字段 非必须
@param sort 排序规则（asc or desc） 非必须
@param fields 返回结果显示字段 (fields=id&fields=title) 非必须
