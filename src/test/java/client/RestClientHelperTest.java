package client;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        String jsonStr = "{\"food\":\"KFC\",\"spicy\":true,\"price\":10}";
        boolean isSuccess = helper.createIndex("food", "dish", "1", jsonStr);
        Assert.assertEquals(isSuccess, true);
    }

    @Test
    public void createIndexBybulk() throws Exception {
        RestClientHelper helper = new RestClientHelper();

        List<String> bulkData = new ArrayList<String>();
        bulkData.add("{\"food\":\"Yellow Chicken Rice\",\"spicy\":true,\"favorite\":{\"location\":\"Beijing\",\"price\":20}}");
        bulkData.add("{\"food\":\"Pumpkin Pie\",\"tags\":[\"pumpkin\",\"sugar\"],\"favorite\":{\"location\":\"Beijing\",\"price\":20}}");
        boolean isSuccess = helper.createIndexBybulk("food", "dish", bulkData);
        Assert.assertEquals(isSuccess, true);
    }

    @Test
    public void delete() throws Exception{
        RestClientHelper helper = new RestClientHelper();
        boolean isSuccess = helper.delete("food", "dish", "1");
        Assert.assertEquals(isSuccess, true);
    }



}