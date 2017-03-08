package com.gochinatv.cdn.api.elasticsearch;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;


public class ElasticSearchTest {

	public static String host = "10.10.7.225";
	public static String index = "vrs";
	public static String type = "video";
	// public static String host="192.168.2.150";

	public static void main(String[] args) {
		// indexExists();
		//index();
		// get();
		mutilGet();
		// update();
		// deleteDocument();
		//deleteIndex();
	}

	public void admin() {
		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, 9300)));

		try {
			ClusterStatsResponse stats = client.admin().cluster().prepareClusterStats().execute().get();
			System.out.println(stats);
		} catch (Exception e) {
			e.printStackTrace();
		}

		client.close();
	}

	public static boolean indexExists() {
		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, 9300)));
		IndicesExistsResponse indicesExistsResponse = client.admin().indices().prepareExists(index).get();
		boolean exists = indicesExistsResponse.isExists();
		System.out.println(exists);
		return exists;
	}

	public static void index() {
		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, 9300)));
		
		DBUtils db = new DBUtils(host, "vrs", "3306", "upenv", "upenv");
        List<Video> resultSet = db.getResultSet(1, 300);
        for (Video video : resultSet) {
        	
    		try {
    			if (!indexExists()) {
    				Settings indexSettings = Settings.settingsBuilder().put("number_of_shards", 3)
    						.put("number_of_replicas", 2).build();
    				CreateIndexRequest indexRequest = new CreateIndexRequest(index, indexSettings);
    				client.admin().indices().create(indexRequest);
    			}
    			
    			Map<String, Object> json = new HashMap<String, Object>();
    			json.put("id", video.getId());
    			json.put("album_id", video.getAlbumId());
    			json.put("src", video.getSrc());
    			json.put("name", video.getName());
    			json.put("subname", video.getSubname());
    			json.put("duration", video.getDuration());
    			json.put("description", video.getDescription());
    			json.put("create_time", video.getCreateTime());
    			json.put("modify_time", video.getModifyTime());
    			json.put("cn_name", video.getCnName());

    			IndexResponse response = client.prepareIndex(index, type, ""+video.getId()).setSource(json).get();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
		}
        client.close();
	}

	public static void get() {
		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, 9300)));
		try {
			GetResponse response = client.prepareGet("water", "org", "1").get();
			System.out.println(response.getSourceAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.close();
	}
	
	
	public static void mutilGet() {
		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, 9300)));
		
		MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
			    .add(index, type, "1578")
			    .add(index, type, "foo")
			    .get();

			for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
			    GetResponse response = itemResponse.getResponse();
			    if (response.isExists()) { 
			        String json = response.getSourceAsString();
			        System.out.println(json);
			    }
			}
		client.close();
	}
	

	public static void update() {
		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, 9300)));
		try {
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("org_code", "zlpr0022");

			UpdateResponse updateResponse = client.prepareUpdate(index, type, "1").setDoc(json).get();
			System.out.println(updateResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.close();
	}

	public static void deleteIndex() {
		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, 9300)));
		DeleteRequest request = new DeleteRequest();
		DeleteIndexResponse reponse = client.admin().indices().prepareDelete(index).get();
		client.close();
	}

	public static void deleteDocument() {
		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, 9300)));
		try {
			DeleteResponse response = client.prepareDelete(index, type, "1").get();
			System.out.println(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.close();
	}

}
