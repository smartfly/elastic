package client;

import com.google.gson.JsonObject;
import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: hutao01
 * Date: 2017/12/15
 * Time: 19:09
 * Description:
 * To change this template use File | Settings | File Templates | Includes | File Header
 */
public class JestClientHelperTest {

    @Test
    public void searchById() throws Exception {
        JestClientHelper helper = new JestClientHelper();
        SearchResult result = helper.searchById("food", "dish", "AV9dBDPR7PzVfTYkGkX9");
        JsonObject jsonObject = result.getJsonObject();
        System.out.println(jsonObject);
    }

    @Test
    public void getById() throws Exception {
        JestClientHelper helper = new JestClientHelper();
        JestResult result = helper.getById("food", "dish", "AV9dBDPR7PzVfTYkGkX9");
        System.out.println(result.getJsonString());
    }

    @Test
    public void createIndex() throws Exception {
        String source = "{\"food\":\"Hot pot\",\"spicy\":true,\"location\":\"sichuan\"}";
        boolean result = new JestClientHelper().createIndex("food", "dish", source);
        Assert.assertEquals(result, true);
    }

    @Test
    public void createIndexBybulk() throws Exception{
        List<String> bulkData = new ArrayList<String>();
        bulkData.add("{\"food\":\"Rice Noodle Rolls\",\"spicy\":true,\"favorite\":{\"location\":\"GuangDong\",\"price\":10}}");
        bulkData.add("{\"food\":\"dumplings\",\"tags\":[\"flour\",\"eggs\",\"vegetables\"]}");
        JestClientHelper helper = new JestClientHelper();
        boolean isSuccess = helper.createIndexBybulk("food", "dish", bulkData);
        Assert.assertEquals(true, isSuccess);
    }

    @Test
    public void search() throws Exception{
        String query = "{\"query\":{\"match\":{\"food\":\"Rice\"}}}";
        JestClientHelper helper = new JestClientHelper();
        SearchResult result = helper.search("food", "dish", query);
        System.out.println(result.getJsonString());
    }

    @Test
    public void delete() throws Exception{
        JestClientHelper helper = new JestClientHelper();
        boolean isSuccess = helper.delete("food", "dish", "AWBaVTZ14gVOZQCksS1D");
        Assert.assertEquals(isSuccess, true);
    }

}