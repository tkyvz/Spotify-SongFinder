package com.utkuyavuz.songfinder.service.implementation;

import com.google.gson.*;
import com.utkuyavuz.songfinder.service.contract.ISpotifyService;
import com.utkuyavuz.songfinder.service.input.SearchItemInput;
import com.utkuyavuz.songfinder.service.output.SearchItemOutput;
import com.utkuyavuz.songfinder.util.GsonUtils;
import com.utkuyavuz.songfinder.util.RestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * Implementation of Spotify Web API operations
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 * @see ISpotifyService
 */
@Service
public class SpotifyService implements ISpotifyService {

    /* Search for an item endpoint */
    private static final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search";

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchItemOutput searchItem(SearchItemInput input) {
        SearchItemOutput output = new SearchItemOutput();

        /* Validate service input */
        if (input == null) {
            output.setStatus(HttpStatus.BAD_REQUEST.value());
            output.setErrorMessage("Service input cannot be null.");
            return output;
        }
        if (!input.isValid()) {
            output.setStatus(HttpStatus.BAD_REQUEST.value());
            output.setErrorMessage("Service input is not valid.");
            return output;
        }

        /* Send HTTP GET request to get the preview url */
        RestUtils restUtils = new RestUtils(SEARCH_ENDPOINT);
        /* Fetch only one result without offset, set market as TR and type as track */
        restUtils
                .addHeader("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("Authorization", "Bearer " + input.getToken())
                .addParameter("q", input.getQuery())
                .addParameter("type", "track")
                .addParameter("market", "TR")
                .addParameter("limit", "1")
                .addParameter("offset", "0")
                .get();

        if (restUtils.isSuccess()) {
            /* HTTP GET is successful */
            String response = restUtils.getResponse();

            /* Parse response from JSON
            {
                "track": {
                    ...,
                    "items":
                        [
                            ...,
                            "preview_url": "URL_TO_FETCH",
                            ...
                        ],
                    ...
                 }
            }
            */
            GsonUtils gsonUtils = new GsonUtils(response);
            gsonUtils
                    .getAsJsonObject("tracks")
                    .getAsJsonArray("items")
                    .getJsonObjectAtIndex(0)
                    .getJsonPrimitive("preview_url");

            if (gsonUtils.isSuccess() && gsonUtils.isJsonPrimitive()) {
                /* Url is fetched */
                JsonPrimitive previewUrl = gsonUtils.getCurrent().getAsJsonPrimitive();
                if (previewUrl.isString()) {
                    /* Fetched url is actually String, return the preview url */
                    output.setPreviewUrl(previewUrl.getAsString());
                    output.setStatus(HttpStatus.OK.value());
                } else {
                    /* Fetched url is not a String, return error */
                    output.setErrorMessage("Resulting object is not a string.");
                    output.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
            } else {
                /* Url cannot be fetched, return error */
                output.setErrorMessage(gsonUtils.getErrorMessage());
                output.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } else {
            /* HTTP GET is not successful, return error */
            output.setErrorMessage(restUtils.getErrorMessage());
            output.setStatus(restUtils.getStatusCode().value());
        }

        return output;
    }
}
