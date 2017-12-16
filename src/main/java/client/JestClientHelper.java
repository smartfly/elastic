package client;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hutao01
 * Date: 2017/12/15
 * Time: 17:47
 * Description: Jest Client
 * To change this template use File | Settings | File Templates | Includes | File Header
 */
public class JestClientHelper {

    static JestClient client;

    static {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig
                        .Builder("http://10.9.192.*:9200")
                        .multiThreaded(true)
                        .build());
        client = factory.getObject();
    }

    /**
     * search by document Id
     * @param index
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public SearchResult getById(String index, String type, String id) throws Exception {
        String query = "{\"query\":{\"match\":{\"_id\":\""+ id +"\"}}}";
        Search search = new Search.Builder(query)
                .addIndex(index)
                .addType(type)
                .build();
        SearchResult result = client.execute(search);
        return result;
    }

    /**
     * create single Index
     * @param index
     * @return
     * @throws Exception
     */
    public boolean createIndex(Index index) throws Exception {
        JestResult result = client.execute(index);
        return (result != null);
    }

}
