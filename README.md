# SearchCrapApi - CrapApi子项目

>此项目用于支持crapapi的全文检索

solr下载地址
http://www.apache.org/dyn/closer.lua/lucene/solr/6.0.1

###新增索引
*请求地址 /api/add
*param data 必须
*data参数格式 [{"id":"1","url":"http://yanxiaowei.com","title":"this is title", "content":"this is the article content"},{"id":"2","url":"http://yanshare.com","title":"this is the other title", "content":"this is the other article content"}]
*data参数为json数组，要求在增加索引前，需要通过solr管理添加所有属性的schema,如 id url title content
*需要注意的是如果id重复，则会覆盖上一条信息

###删除索引

###修改索引

###查询索引
