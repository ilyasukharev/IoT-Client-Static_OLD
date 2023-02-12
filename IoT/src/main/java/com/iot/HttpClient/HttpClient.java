package com.iot.HttpClient;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
public class HttpClient {
    public void makePostRequest(JSONObject obj, String underPath) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            final String patch = "https://httpbin.org/post/" + underPath;
            HttpPost post = new HttpPost(patch);
            final StringEntity entity = new StringEntity(obj.toString());
            post.setEntity(entity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = httpClient.execute(post);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            httpClient.close();
        }
    }
}