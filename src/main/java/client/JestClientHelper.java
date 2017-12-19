package client;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

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
    public SearchResult searchById(String index, String type, String id) throws Exception {
        String query = "{\"query\":{\"match\":{\"_id\":\""+ id +"\"}}}";
        Search search = new Search.Builder(query)
                .addIndex(index)
                .addType(type)
                .build();
        SearchResult result = client.execute(search);
        return result;
    }

    /**
     * get by document Id
     * @param index
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public JestResult getById(String index, String type, String id) throws Exception {
        Get get = new Get.Builder(index, id).type(type).build();
        JestResult result = client.execute(get);
        return result;
    }

    /**
     * index documents API
     * @param index
     * @param type
     * @param source
     * @return
     * @throws Exception
     */
    public boolean createIndex(String index, String type, String source) throws Exception {
        Index newIndex = new Index.Builder(source).index(index).type(type).build();
        JestResult result = client.execute(newIndex);
        return (result.getResponseCode() == HttpStatus.SC_OK);
    }

    /**
     * Bulk Operations API
     * @param index
     * @param type
     * @param bulkData
     * @return
     * @throws Exception
     */
    public boolean createIndexBybulk(String index, String type, List<String> bulkData) throws Exception {
        List<Index> lists = new ArrayList<Index>(bulkData.size());
        for (String item : bulkData) {
            lists.add(new Index.Builder(item).build());
        }
        Bulk bulk = new Bulk.Builder()
                .defaultIndex(index)
                .defaultType(type)
                .addAction(lists)
                .build();
        JestResult result = client.execute(bulk);
        return (result.getResponseCode() == HttpStatus.SC_OK);
    }

    /**
     * search documents API
     * @param index
     * @param type
     * @param query
     * @return
     * @throws Exception
     */
    public SearchResult search(String index, String type, String query) throws Exception {
        Search search = new Search.Builder(query)
                .addIndex(index)
                .addType(type)
                .build();
        SearchResult result = client.execute(search);
        return result;
    }

    /**
     * delete by document id API
     * @param index
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(String index, String type, String id) throws Exception {
        Delete deleteIndex = new Delete.Builder(id)
                .index(index)
                .type(type)
                .build();
        JestResult result = client.execute(deleteIndex);
        return (result != null);
    }

}
