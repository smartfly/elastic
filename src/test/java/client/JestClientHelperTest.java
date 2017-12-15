package client;

import com.google.gson.JsonObject;
import io.searchbox.core.Index;
import io.searchbox.core.SearchResult;
import org.junit.Assert;
import org.junit.Test;

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
    public void getById() throws Exception {
        JestClientHelper helper = new JestClientHelper();
        SearchResult result = helper.getById("food", "dish", "AV9dBDPR7PzVfTYkGkX9");
        JsonObject jsonObject = result.getJsonObject();
        System.out.println(jsonObject);
    }

    @Test
    public void createIndex() throws Exception {

        Index index = new Index.Builder("{\"food\":\"Hot pot\",\"spicy\":true,\"location\":\"sichuan\"}")
                .index("food")
                .type("dish")
                .id("2")
                .build();
        boolean result = new JestClientHelper().createIndex(index);
        Assert.assertEquals(result, true);
    }

}