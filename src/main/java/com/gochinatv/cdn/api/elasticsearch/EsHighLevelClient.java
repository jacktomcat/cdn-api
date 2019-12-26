package com.gochinatv.cdn.api.elasticsearch;

import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

/**
 * ${DESCRIPTION}
 * ES 高级别的api
 * @auhtor jacktomcat
 * @create 2019-01-25 下午9:38
 */
public class EsHighLevelClient {

    static RestHighLevelClient client;

    static String index = "video";

    public static void main(String[] args) {
        initElasticSearchClient();
        try {
            //mockData();
            search();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void initElasticSearchClient() {
        String userName = "";
        String password = "";
        int socketTimeout = 3000;
        int connectTimeout = 3000;
        int maxRetryTimeoutMillis = 3000;
        int connectionRequestTimeout = 3000;

        String queryUrl = "http://192.168.2.160:9200";

        HttpHost[] httpHosts = Arrays.stream(queryUrl.split(",")).map(HttpHost::create).toArray(HttpHost[]::new);

        RestClientBuilder clientBuilder = RestClient.builder(httpHosts)/*.setRequestConfigCallback(
                requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(connectTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectionRequestTimeout(connectionRequestTimeout)
        )*/.setMaxRetryTimeoutMillis(maxRetryTimeoutMillis);
        if (userName!=null && !userName.equals("") && password!=null && !password.equals("")) {
            BasicCredentialsProvider bcp = new BasicCredentialsProvider();
            bcp.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(userName, password));
            clientBuilder.setHttpClientConfigCallback(httpClientConfig -> httpClientConfig.setDefaultCredentialsProvider(bcp));
        }
        client = new RestHighLevelClient(clientBuilder);
    }

    /**
     * curl -X 'DELETE' http://192.168.2.160:9200/video;
     *
     * curl -H "Content-Type:application/json" -X PUT --data '{
     *   "settings": {
     *     "number_of_shards": 10,
     *     "number_of_replicas": 1
     *   },
     *   "mappings": {
     *     "_doc": {
     *       "properties": {
     *         "name": {
     *           "type": "keyword"
     *         },
     *         "duration": {
     *           "type": "integer"
     *         },
     *         "describe": {
     *           "type": "keyword"
     *         },
     *         "plot": {
     *           "type": "text"
     *         },
     *         "timestamp": {
     *           "type": "date"
     *         }
     *       }
     *     }
     *   }
     * }' http://192.168.2.160:9200/video
     * @throws Exception
     */
    public static void mockData() throws Exception{
        BulkRequest bulkRequest = new BulkRequest();
        AtomicLong eventAtomic = new AtomicLong(System.currentTimeMillis());
        String[] name = {"寄生虫","我不是药神"};
        String[] describe = {"一边是水淹陋室，一边是生日聚会","王传君所有不被外人理解的坚持，都在这一刻得到了完美释放"};
        String[] plot = {"基宇（崔宇植 饰）出生在一个贫穷的家庭之中，和妹妹基婷（朴素丹 饰）以及父母在狭窄的地下室里过着相依为命的日子。一天，基宇的同学上门拜访，他告诉基宇，自己在一个有钱人家里给他们的女儿做家教，太太是一个头脑简单出手又阔绰的女人，因为自己要出国留学，所以将家教的职位暂时转交给基宇。\n" +
                "　　就这样，基宇来到了朴社长（李善均 饰）家中，并且见到了他的太太（赵汝贞 饰），没过多久，基宇的妹妹和父母也如同寄生虫一般的进入了朴社长家里工作。然而，他们的野心并没有止步于此，基宇更是和大小姐坠入了爱河。随着时间的推移，朴社长家里隐藏的秘密渐渐浮出了水面。" ,
                "普通中年男子程勇（徐峥 饰）经营着一家保健品店，失意又失婚。不速之客吕受益（王传君 饰）的到来，让他开辟了一条去印度买药做“代购”的新事业，虽然困难重重，但他在这条“买药之路”上发现了商机，一发不可收拾地做起了治疗慢粒白血病的印度仿制药独家代理商。赚钱的同时，他也认识了几个病患及家属，为救女儿被迫做舞女的思慧（谭卓 饰）、说一口流利“神父腔”英语的刘牧师（杨新鸣 饰），以及脾气暴烈的“黄毛”（章宇 饰），几个人合伙做起了生意，利润倍增的同时也危机四伏。程勇昔日的小舅子曹警官（周一围 饰）奉命调查仿制药的源头，假药贩子张长林（王砚辉 饰）和瑞士正牌医药代表（李乃文 饰）也对其虎视眈眈，生意逐渐变成了一场关于救赎的拉锯战"};

        for(int i=0;i<2;i++){
            DateTime timestamp = DateTime.now().plusMinutes(RandomUtils.nextInt(0,8));
            long id = eventAtomic.incrementAndGet();
            Map<String, Object> data = new HashMap<>();
            data.put("id",  id);
            data.put("name",name[i]);
            data.put("duration",RandomUtils.nextInt(30,60));
            data.put("describe",describe[i]);//简介
            data.put("plot",plot[i]);//剧情
            data.put("timestamp",timestamp.getMillis());

            IndexRequest indexRequest = new IndexRequest(index,"_doc", ""+id).source(data);
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest);
        client.close();
    }


    /**
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.2/analysis.html  默认分词器
     * @throws Exception
     */
    public static void search() throws  Exception{
        SearchSourceBuilder builder = SearchSourceBuilder.searchSource().fetchSource(true);
        BoolQueryBuilder query = boolQuery();

        //query.must(QueryBuilders.rangeQuery("duration").gt(30).lt(60));//范围查询
        //query.must(QueryBuilders.termQuery("duration",58));//精确查询
        //query.mustNot(QueryBuilders.termQuery("duration",57));//not in
        /**
         * 我们可以通过指定一个boost值来控制每个查询子句的相对权重
         */
        //query.boost(5);

        //https://www.cnblogs.com/sunfie/p/6653778.html
        //query.must(QueryBuilders.wildcardQuery("name","寄生*"));//模糊查询
        //query.must(QueryBuilders.matchQuery("name","寄生*"));
        //query.must(QueryBuilders.regexpQuery("name","寄生[虫]"));//正则
        //query.must(QueryBuilders.scriptQuery(new Script("doc['targetType'].value")));
        //query.must(QueryBuilders.prefixQuery("name","寄"));//以寄开头的条件进行查询
        //query.must(QueryBuilders.idsQuery("1","2"));//按id查询

        /**
         * 说明：fuzzy才是实现真正的模糊查询，我们输入的字符可以是个大概，他可以根据我们输入的文字大概进行匹配查询，
         * 具体可看文章中的解释和代码，注意与wildcard模糊查询的区别
         */
        query.must(QueryBuilders.fuzzyQuery("name","寄"));
        //query.must(QueryBuilders.matchPhraseQuery());
        //query.must(QueryBuilders.spanTermQuery("",""));

        builder.fetchSource(new String[]{"duration","plot","name","id","describe","timestamp"},null);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        builder.query(query);
        searchRequest.source(builder);
        System.out.println(query.toString());
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hisList = hits.getHits();
        for (SearchHit hit : hisList) {
            System.out.println(hit.getSourceAsString());
        }
        client.close();
    }

    @BeforeTest
    public void beforeInit() {
        initElasticSearchClient();
    }

    @Test
    public void testInsertRoutingKey(){

        try {
            BulkRequest bulkRequest = new BulkRequest();
            AtomicLong eventAtomic = new AtomicLong(System.currentTimeMillis());
            for(int i=0;i<=1;i++){
                long sid = eventAtomic.incrementAndGet();
                Map<String, Object> data = new HashMap<>();
                data.put("id",  sid);
                data.put("plot", "第二级");
                data.put("timestamp",new Date().getTime());
                data.put("name", "zhangsan");
                data.put("duration",100); //0和1
                data.put("describe","第二级");
                IndexRequest indexRequest = new IndexRequest("video_2019","_doc", ""+sid).source(data).routing("zhuhh");
                bulkRequest.add(indexRequest);
            }
            client.bulk(bulkRequest);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetRoutingKey(){
        SearchSourceBuilder builder = SearchSourceBuilder.searchSource().fetchSource(true);
        BoolQueryBuilder query = boolQuery();
        query.must(QueryBuilders.termQuery("name", "zhangsan"));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("video_2019");
        builder.query(query);
        searchRequest/*.routing("zhuhh")*/.source(builder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
            SearchHits hits = searchResponse.getHits();
            int length = hits.getHits().length;
            Assert.assertEquals(length,1);
            for(int i=0;i<length;i++) {
                SearchHit hit = hits.getHits()[i];
                System.out.println(hit.getSourceAsMap());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
