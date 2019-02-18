//package  com.configuration;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import org.elasticsearch.Version;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.settings.Settings.Builder;
//import org.elasticsearch.env.Environment;
//import org.elasticsearch.node.InternalSettingsPreparer;
//import org.elasticsearch.node.Node;
//import org.elasticsearch.node.NodeValidationException;
//import org.elasticsearch.plugins.Plugin;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//
//import com.example.demo.elastic.*;
//
//@Configuration
//public class ElasticSearchConfiguration {
//
//        //load value from application.property
//	    @Value("${spring.data.elasticsearch.cluster-name}")
//	    private String clusterName;
//	    
//		@SuppressWarnings("resource")
//		public Client nodeClient() throws NodeValidationException {
//			
//			Builder settings = Settings.builder();
//			//enable bind on localhost:9200 for node client
//            settings.put("node.local", true);
//            settings.put("transport.type","netty4");
//            settings.put("cluster.name", clusterName);
//            //expose elasticsearch on http://localhost:9200
//            settings.put("http.enabled", true);
//            settings.put("path.home", "data");
//            settings.put("path.log", "log");
//            settings.put("index.analysis.version", "1.0.0");
//
//            //Create node client with plugins, add plugins to built in elsticsearch
//            Node node = new NodeWithPlugins(InternalSettingsPreparer.prepareEnvironment(settings.build(), null), Version.CURRENT,
//                    Collections.singletonList(SerbianPlugin.class)).start();
//			return node.client();
//		}
//	    
//	    @Bean
//	    public ElasticsearchTemplate elasticsearchTemplate() throws NodeValidationException {
//	        return new ElasticsearchTemplate(nodeClient());
//	    }
//
//	
//}
