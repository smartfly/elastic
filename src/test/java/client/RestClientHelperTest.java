package client;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hutao01
 * Date: 2017/12/15
 * Time: 15:29
 * Description:
 * To change this template use File | Settings | File Templates | Includes | File Header
 */
public class RestClientHelperTest {

    @Test
    public void getById() {
        RestClientHelper helper = new RestClientHelper();
        try {
            Response response = helper.getById("food", "dish", "AV9dBDPR7PzVfTYkGkX9");
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void put() throws Exception {
        RestClientHelper helper = new RestClientHelper();
        HttpEntity entity = new NStringEntity(
                "{\"food\":\"KFC\",\"spicy\":true,\"price\":10}",
                ContentType.APPLICATION_JSON);
        boolean isSuccess = helper.put("food", "dish", "1", entity);
        Assert.assertEquals(isSuccess, true);
    }



}