# SearchCrapApi - CrapApi子项目

>此项目用于支持crapapi的全文检索

##部署solr
1、下载solr
```
下载地址http://www.apache.org/dyn/closer.lua/lucene/solr/6.0.1
```
2、解压
```
tar xvfz solr-6.0.1.tgz
```
3、启动solr

如果使用localhost访问
```
./solr start
```
如果使用域名访问(-p表示端口，如无80端口，可以使用nginx代理，-h表示主机名，也就是我们说的域名)
```
./solr start -p 80 -h www.yanxiaowei.com
```
4、创建core
```
./solr creat -c api
```
5、done

##检出项目

1、检出项目

2、修改配置文件

main/resources/search.properties
```
#solr地址
solr.url = http://localhost:8983/solr/api
solr.queueSize = 10
solr.threadCount = 10
#接口账号及密码
api.username = api
api.password = api
```
##操作接口
###新增索引
**请求地址 /api/add**
```
/**
  * @param data 必须
  * @description data参数格式 [{"id":"1","url":"http://yanxiaowei.com","title":"this is title", "content":"this is the article   content"},{"id":"2","url":"http://yanshare.com","title":"this is the other title", "content":"this is the other article content"}]
  * @description data参数为json数组，要求在增加索引前，需要通过solr管理添加所有属性的schema,如 id url title content
  * @description 需要注意的是如果id重复，则会覆盖上一条信息
 **/
```
**返回结果** 
```
{
  "code": 0,
  "message": "success index 2 data"
}
```
###删除索引
**请求地址 /api/delete**
```
/**
  * @param ids 索引数组 必须
  * @description 参数格式 ["1","2"]
 **/
```
**返回结果** 
```
{
  "code": 0,
  "message": "success delete 2 index"
}
```
###修改索引
**请求地址 /api/update**
```
/**
  * @param data 必须
  * data参数格式 [{"id":"1","url":"http://yanxiaowei.com","title":"this is title", "content":"this is the article content"},{"id":"2","url":"http://yanshare.com","title":"this is the other title", "content":"this is the other article content"}]
  * data参数为json数组，要求在增加索引前，需要通过solr管理添加所有属性的schema,如 id url title content
  * 更新索引的原理为先删除旧的索引，然后创建新索引
 **/
```
**返回结果**
```
{
  "code": 0,
  "message": "success update 2 index"
}
```
###查询索引
**请求地址 /api/query**
```
/**
  * @param param 查询关键字 必须
  * @param count 最多返回的结果数 非必须
  * @param start 从第几条查起 非必须
  * @param hl 是否高亮 非必须
  * @param sortfield 排序字段 非必须
  * @param sort 排序规则（asc or desc） 非必须
  * @param fields 返回结果显示字段 (fields=id&fields=title) 非必须
 **/
 ```
**返回结果** 
 ```
 {
  "code": 0,
  "message": "success",
  "data": {
    "numfound": 1,
    "response": [
      {
        "id": "002",
        "url": [
          "http://yanxiaowei.com"
        ],
        "content": [
          "湖南的岳阳"
        ]
      }
    ],
    "start": 0,
    "elapsedtime": 13,
    "highlighting": {
      "002": {
        "content": [
          "<em class='hl-key'>湖</em><em class='hl-key'>南</em>的岳阳"
        ]
      }
    }
  }
}
//highlighting 高亮内容
//numfound 返回结果数
//response 返回内容
 ```
