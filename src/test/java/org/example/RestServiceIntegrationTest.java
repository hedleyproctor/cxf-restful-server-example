package org.example;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Shows how to test a CXF server by using Apache Commons http client to
 * send data to the server and check the response.
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:cxf-beans.xml")
public class RestServiceIntegrationTest {

    @Test
    public void greet() throws IOException {
        Pair<Integer, String> response = sendHttpRequest("GET", "greet", "text/plain", null);
        assertEquals(200, response.getLeft());
        assertEquals("Hi There!!", response.getRight());
    }

    @Test
    public void sayHello() throws IOException {
        Pair<Integer, String> response = sendHttpRequest("POST", "sayhello", "application/json", "Jane");
        assertEquals(200, response.getLeft());
        assertEquals("{\"hello\":\"Hello\",\"name\":\"Jane\"}", response.getRight());
    }

    @Test
    public void submit() throws IOException {
        Pair<Integer, String> response = sendHttpRequest("POST", "submit", "application/json",
                "{\"id\": 123,\"claimNumber\":\"CLM123\"}");
        assertEquals(200, response.getLeft());
        assertEquals("{\"fraudScore\":200,\"details\":\"This claim looks dodgy\"}", response.getRight());
    }

    private Pair<Integer,String> sendHttpRequest(String httpType, String operation, String contentType, String input) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // Create an HTTP request
            ClassicHttpRequest httpRequest = null;
            if (httpType.equals("GET")) {
                httpRequest = new HttpGet("http://localhost:8080/cxf-rest/hello/" + operation);
            } else if (httpType.equals("POST")) {
                httpRequest = new HttpPost("http://localhost:8080/cxf-rest/hello/" + operation);
            }
            httpRequest.setHeader("Content-Type", contentType);

            // Set payload
            httpRequest.setEntity(new StringEntity(input));

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                // Print the status code
                System.out.println("HTTP Status Code: " + response.getCode());

                // Get the response body as a string
                return Pair.of(response.getCode(), EntityUtils.toString(response.getEntity()));
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
