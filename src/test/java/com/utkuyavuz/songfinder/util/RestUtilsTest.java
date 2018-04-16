package com.utkuyavuz.songfinder.util;

import com.google.gson.JsonPrimitive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
public class RestUtilsTest {

    /* Public HTTP Request test endpoint */
    private static final String ENDPOINT = "https://httpbin.org/get";
    /* Authorization header value */
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer HIDDEN_TOKEN";
    /* Query Parameter */
    private static final String QUERY_PARAMETER = "param";
    /* Query Parameter value */
    private static final String QUERY_PARAMETER_VALUE = "value";

    private RestUtils restUtils;
    private String response;

    /**
     * Get response for HTTP GET to ENDPOINT with headers
     *  - Content-Type: application/json;charset=UTF-8
     *  - Authorization: AUTHORIZATION_HEADER_VALUE
     *
     *  and query string
     *  - QUERY_PARAMETER=QUERY_PARAMETER_VALUE
     */
    @Before
    public void initialize() {
        restUtils = new RestUtils(ENDPOINT);
        restUtils
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("Authorization", AUTHORIZATION_HEADER_VALUE)
                .addParameter(QUERY_PARAMETER, QUERY_PARAMETER_VALUE);
        restUtils.get();
        this.response = restUtils.getResponse();
    }

    @Test
    public void getTest() {
        /* Check operation is successful, raw response has length > 0, response is not null */
        assert (restUtils.isSuccess());
        assert (restUtils.getRawResponse().length > 0);
        assert (restUtils.getResponse() != null);

        /* After parsing to String then converting to byte[] should not change the length of the byte[] */
        byte[] byteResponse = restUtils.getResponse().getBytes(StandardCharsets.UTF_8);
        assert (byteResponse.length == restUtils.getRawResponse().length);
    }

    @Test
    public void parseQueryParameterTest() {
        /* Check the query string is really QUERY_PARAMETER=QUERY_PARAMETER_VALUE */
        GsonUtils gsonUtils = new GsonUtils(this.response);
        gsonUtils
                .getAsJsonObject("args")
                .getJsonPrimitive(QUERY_PARAMETER);
        assert (gsonUtils.isSuccess());
        assert (gsonUtils.isJsonPrimitive());
        JsonPrimitive element = gsonUtils.getCurrent().getAsJsonPrimitive();
        assert (element.isString());
        assert (element.getAsString().equals(QUERY_PARAMETER_VALUE));
    }

    @Test
    public void parseAuthorizationHeaderTest() {
        /* Check the Authorization header's value is really AUTHORIZATION_HEADER_VALUE */
        GsonUtils gsonUtils = new GsonUtils(this.response);
        gsonUtils
                .getAsJsonObject("headers")
                .getJsonPrimitive("Authorization");
        assert (gsonUtils.isSuccess());
        assert (gsonUtils.isJsonPrimitive());
        JsonPrimitive element = gsonUtils.getCurrent().getAsJsonPrimitive();
        assert (element.isString());
        assert (element.getAsString().equals(AUTHORIZATION_HEADER_VALUE));
    }
}
