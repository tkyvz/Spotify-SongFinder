package com.utkuyavuz.songfinder.service.input;

import org.springframework.util.StringUtils;

/**
 * Input class for {@link com.utkuyavuz.songfinder.service.contract.ISpotifyService#searchItem(SearchItemInput)}
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public class SearchItemInput {

    /** Query to search in Spotify Web API*/
    private String query;
    /** Spotify Web API token */
    private String token;

    /**
     * The default constructor
     */
    public SearchItemInput() {

    }

    /**
     * The constructor with fields
     *
     * @param query Query to be searched in the Spotify Web API <em>search for an item</em> endpoint as {@link String}
     * @param token Access token for the Spotify Web API as {@link String}
     */
    public SearchItemInput(String query, String token) {
        this.query = query;
        this.token = token;
    }

    /**
     * Gets the query string to be searched in the Spotify Web API
     *
     * @return The query string to be searched
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the query string to be searched in the Spotify Web API
     *
     * @param query The query string to be searched
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Gets the access token for the Spotify Web API
     *
     * @return The access token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the access token for the Spotify Web API
     *
     * @param token The access token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Validates the instance for
     * {@link com.utkuyavuz.songfinder.service.contract.ISpotifyService#searchItem(SearchItemInput)} method
     *
     * @return Returns <code>true</code> if {@link #query} and {@link #token} are not empty, else <code>false</code>
     */
    public boolean isValid() {
        if (StringUtils.isEmpty(this.query)) {
            return false;
        }
        if (StringUtils.isEmpty(this.token)) {
            return false;
        }
        return true;
    }
}
