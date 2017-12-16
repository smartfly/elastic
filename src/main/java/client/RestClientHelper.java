package client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.sniff.Sniffer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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

    static Sniffer sniffer;

    private static final String SEPARATOR = "/";

    private static final String LINEFEED = "\n";

    static {
       client = RestClient.builder(
               new HttpHost("10.9.192.*", 9200, "http"),
               new HttpHost("10.9.197.*", 9200, "http")
       ).build();
       sniffer = Sniffer.builder(client).build();
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
     * @param jsonData
     * @return
     * @throws Exception
     */
    public boolean createIndex(String index, String type, String id, String jsonData) throws Exception {

        HttpEntity entity = new NStringEntity(jsonData,
                ContentType.APPLICATION_JSON);

        Response response = client.performRequest(
                "PUT",
                SEPARATOR + index + SEPARATOR + type + SEPARATOR + id,
                Collections.<String, String>emptyMap(),
                entity);
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    /**
     * Bulk API
     * @param index
     * @param type
     * @param bulkData
     * @return
     * @throws IOException
     */
    public boolean createIndexBybulk(String index, String type, List<String> bulkData) throws Exception {
        String actionMetaData =
                String.format("{ \"index\" : { \"_index\" : \"%s\", \"_type\" : \"%s\" } }%n", index, type);

        StringBuilder bulkRequestBody = new StringBuilder();
        for (String bulkItem : bulkData) {
            bulkRequestBody.append(actionMetaData);
            bulkRequestBody.append(bulkItem);
            bulkRequestBody.append(LINEFEED);
        }
        HttpEntity entity = new NStringEntity(
                bulkRequestBody.toString(),
                ContentType.APPLICATION_JSON);

        String endPoint = String.format("/%s/%s/_bulk", index, type);

        Response response = client.performRequest("POST",
                endPoint,
                Collections.<String, String>emptyMap(),
                entity);
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }


    /**
     * update by document Id
     * @param index
     * @param type
     * @param id
     * @param jsonData
     * @return
     * @throws Exception
     */
    public boolean update(String index, String type, String id, String jsonData) throws Exception {
        String endPoint = String.format("/%s/%s/%s", index, type, id);
        HttpEntity entity = new NStringEntity(jsonData, ContentType.APPLICATION_JSON);
        Response response = client.performRequest("POST",
                endPoint,
                Collections.<String, String>emptyMap(),
                entity);
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    /**
     * Delete By document Id API
     * @param index
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    public boolean delete(String index, String type, String id) throws Exception {
        String endPoint = String.format("/%s/%s/%s", index, type, id);
        Response response = client.performRequest("DELETE", endPoint);
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    public Response checkClusterStatus() throws Exception {
        Response response = client.performRequest("GET", "/", Collections.singletonMap("pretty", "true"));
        return response;
    }

    /**
     * Close elastic client API
     * @return
     */
    public boolean close(){
        try {
            client.close();
            sniffer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
