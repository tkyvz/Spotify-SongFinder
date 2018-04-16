package com.utkuyavuz.songfinder.service.contract;

import com.utkuyavuz.songfinder.service.input.SearchItemInput;
import com.utkuyavuz.songfinder.service.output.SearchItemOutput;

/**
 * Service interface for Spotify Web API operations
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public interface ISpotifyService {

    /**
     * Gets the preview url or operation error for the given song using Spotify <em>search for an item</em> endpoint.
     *
     * @param input The searchItem's input {@link SearchItemInput}
     * @return A {@link SearchItemOutput} object containing the status code and the service output or error description
     */
    public SearchItemOutput searchItem(SearchItemInput input);
}
