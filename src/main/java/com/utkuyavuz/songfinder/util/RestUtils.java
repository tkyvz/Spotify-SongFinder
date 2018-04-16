package com.utkuyavuz.songfinder.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * A utility class to send HTTP requests
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public class RestUtils {

    /* The Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);

    /* Uri Builder */
    private UriComponentsBuilder uri;
    /* RestTempplate */
    private RestTemplate restTemplate;
    /* Headers */
    private HttpHeaders headers;
    /* Response StatusCode */
    private HttpStatus statusCode;
    /* Raw response */
    private byte[] rawResponse;
    /* Error message */
    private String errorMessage;
    /* Operation succeeded or not */
    private boolean success;
    /* Instance is used or not */
    private boolean used;

    /**
     * Initialize RestUtils with a uri
     *
     * @param uri The uri that the request will be sent
     */
    public RestUtils(String uri) {
        LOGGER.debug("Initializing RestUtils with URI[" + uri + "]");
        this.uri = UriComponentsBuilder.fromHttpUrl(uri);
        this.restTemplate = new RestTemplateBuilder().build();
        this.headers = new HttpHeaders();
        this.statusCode = null;
        this.rawResponse = null;
        this.errorMessage = null;
        this.success = false;
        this.used = false;
    }

    /**
     * Sets the given request header with the given value
     *
     * @param name Name of the header
     * @param value Value of the header
     * @return A {@link RestUtils} instance
     */
    public RestUtils addHeader(String name, String value) {
        LOGGER.debug("Set Header[" + name + "] = " + value);
        this.headers.set(name, value);
        return this;
    }

    /**
     * Sets the given query parameter with the given value
     *
     * @param key Key of the query parameter
     * @param value Value of the query parameter
     * @return A {@link RestUtils} instance
     */
    public RestUtils addParameter(String key, String value) {
        LOGGER.debug("Set QueryParameter[" + key + "] = " + value);
        this.uri.queryParam(key, value);
        return this;
    }

    /**
     * Execute HTTP GET method
     *
     * @return A {@link RestUtils} instance
     */
    public RestUtils get() {
        /* Instance is already consumed, do nothing */
        if (this.used) {
            LOGGER.error("RestUtils has already been consumed.");
            this.success = false;
            this.errorMessage = "RestUtils has already been consumed.";
            return this;
        }
        HttpEntity entity = new HttpEntity(headers);
        try {
            LOGGER.debug("Sending HTTP GET request - URI[" + this.uri.toUriString() + "]");
            ResponseEntity<byte[]> response =
                    this.restTemplate.exchange(new URI(this.uri.toUriString()), HttpMethod.GET, entity, byte[].class);
            this.rawResponse = response.getBody();
            this.statusCode = response.getStatusCode();
            this.success = true;
            LOGGER.debug("Retrieved HTTP GET response - URI[" + this.uri.toUriString()
                    + "] - Response[" + this.getResponse() + "]");
        } catch (URISyntaxException e) {
            /* Cannot parse the URI */
            LOGGER.error("Exception while executing HTTP GET request - Exception: " + e.getMessage(), e);
            this.statusCode = HttpStatus.NOT_FOUND;
            this.success = false;
            this.errorMessage = "Invalid URI[" + this.uri.toUriString() + "].";
        } catch (HttpClientErrorException exception) {
            /* Operation encountered an HttpError */
            this.statusCode = exception.getStatusCode();
            this.success = false;

            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                /* StatusCode = 404 */
                this.errorMessage =  "Endpoint [" + uri + "] not found.";
                LOGGER.error("Server encountered an error. StatusCode[" + exception.getStatusCode() +
                        "] - Message[Endpoint [" + uri + "] not found.]", exception);
            } else if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                /* StatusCode = 401 */
                this.errorMessage = "Unauthorized, try to renew access token.";
                LOGGER.error("Server encountered an error. StatusCode[" + exception.getStatusCode() +
                        "] - Message[Unauthorized, try to renew access token]", exception);
            } else {
                /* StatusCode NOT IN { 200, 401, 404 } */
                this.errorMessage = "Server encountered an error. StatusCode[" + exception.getStatusCode() +
                        "] - Message[" + exception.getMessage() + "]";
                LOGGER.error("Server encountered an error. StatusCode[" + exception.getStatusCode() +
                        "] - Message[" + exception.getMessage() + "]", exception);
            }
        } finally {
            LOGGER.debug("Set RestUtils as used.");
            used = true;
        }
        return this;
    }

    /**
     * Execute HTTP POST method
     * <br/> <strong>DO NOT USE THIS METHOD AS IT IS NOT IMPLEMENTED YET</strong>
     *
     * @return A {@link RestUtils}
     */
    public RestUtils post() {
        LOGGER.error("post() method is not implemented.");
        throw new NotImplementedException();
    }

    /**
     * Execute HTTP PUT method
     * <br/> <strong>DO NOT USE THIS METHOD AS IT IS NOT IMPLEMENTED YET</strong>
     *
     * @return A {@link RestUtils}
     */
    public RestUtils put() {
        LOGGER.error("put() method is not implemented.");
        throw new NotImplementedException();
    }

    /**
     * Execute HTTP DELETE method
     * <br/> <strong>DO NOT USE THIS METHOD AS IT IS NOT IMPLEMENTED YET</strong>
     *
     * @return A {@link RestUtils}
     */
    public RestUtils delete() {
        LOGGER.error("delete() method is not implemented.");
        throw new NotImplementedException();
    }

    /**
     * Check whether the current instance has encountered an error or not.
     *
     * @return Returns <code>true</code> if there exists no errors, else <code>false</code>
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the HttpStatus
     *
     * @return The HttpStatus
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the raw HTTP Response as <code>byte[]</code>
     *
     * @return The raw HTTP Response
     */
    public byte[] getRawResponse() {
        return rawResponse;
    }

    /**
     * Gets the HTTP Response as {@link String}
     *
     * @return The HTTP Response
     */
    public String getResponse() {
        if (!this.used) {
            LOGGER.error("RestUtils has not been consumed yet.");
            return "";
        }
        if (!this.success) {
            LOGGER.error("RestUtils has encountered an error.");
            return "";
        }

        return new String(rawResponse, StandardCharsets.UTF_8);
    }

    /**
     * Gets the error message
     *
     * @return The error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
