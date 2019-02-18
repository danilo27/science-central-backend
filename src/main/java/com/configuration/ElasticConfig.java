//finished UDD

//package com.configuration;
//
//import java.net.InetAddress;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.plugins.Plugin;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.example.demo.elastic")
//public class ElasticConfig {
//	 @Value("${elasticsearch.host}")
//	    private String esHost;
//
//	    @Value("${elasticsearch.port}")
//	    private int esPort;
//
//	    @Value("${elasticsearch.clustername}")
//	    private String esClusterName;
//
//	    @Bean
//	    public Client client() throws Exception {
//	        Settings settings = Settings.builder().put("cluster.name", esClusterName).build();
//	        Collection<Class<? extends Plugin>> plugins = new ArrayList<>();
//	         
//	        TransportClient client = new PreBuiltTransportClient(settings);
//	        client.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
//	        return client;
//	    }
//
//	    @Bean
//	    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
//	        return new ElasticsearchTemplate(client());
//	    }
//	    	
////	    @Bean
////	    public RestClient restClient(){
////	    RestClient restClient = RestClient.builder(
////	    	       new HttpHost("localhost", 9200, "http"),
////	    	       new HttpHost("localhost", 9205, "http")).build();
////	    	return restClient;
////	    }
//	    
//	    @Bean
//	    public RestHighLevelClient restHighLevelClient() {
//
//	        RestHighLevelClient client = new RestHighLevelClient(
//	                RestClient.builder(new HttpHost("localhost",9200,"http")));
//
//	        return client;
//
//	    }
//	    
//	    
//	    
//	    
//    //Embedded Elasticsearch Server
//    /*@Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
//    }*/
//}
