package client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hutao01
 * Date: 2017/12/15
 * Time: 14:40
 * Description: Rest Client
 * To change this template use File | Settings | File Templates | Includes | File Header
 */
public class RestClientHelper {

    static RestClient client;

    private static final String SEPARATOR = "/";

    static {
       client = RestClient.builder(
               new HttpHost("10.9.192.182", 9200, "http"),
               new HttpHost("10.9.197.165", 9200, "http")
       ).build();
    }

    /**
     * search by document Id
     * @param index
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public Response getById(String index, String type, String id) throws Exception {
        String searchEndPoint = new StringBuffer()
                .append("/").append(index)
                .append("/").append(type)
                .append("/_search")
                .toString();

        HttpEntity entity = new NStringEntity(
                "{\"query\":{\"match\":{\"_id\":\"" + id + "\"}}}",
                ContentType.APPLICATION_JSON);

        Response response = client.performRequest("GET",
                searchEndPoint,
                Collections.singletonMap("pretty", "true"),
                entity);
        return response;
    }

    /**
     * insert index by document Id
     * @param index
     * @param type
     * @param id
     * @param entity
     * @return
     * @throws Exception
     */
    public boolean put(String index, String type, String id, HttpEntity entity) throws Exception {
        Response indexRepnse = client.performRequest(
                "PUT",
                SEPARATOR + index + SEPARATOR + type + SEPARATOR + id,
                Collections.<String, String>emptyMap(),
                entity);
        return (indexRepnse != null);
    }

}
