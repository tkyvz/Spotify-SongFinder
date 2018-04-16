package com.utkuyavuz.songfinder.restcontroller;

import com.utkuyavuz.songfinder.model.SongFinderError;
import com.utkuyavuz.songfinder.service.contract.IPreviewService;
import com.utkuyavuz.songfinder.service.contract.ISpotifyService;
import com.utkuyavuz.songfinder.service.input.PreviewInput;
import com.utkuyavuz.songfinder.service.input.SearchItemInput;
import com.utkuyavuz.songfinder.service.output.PreviewOutput;
import com.utkuyavuz.songfinder.service.output.SearchItemOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Rest controller class for Spotify Rest Web methods
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
@RestController
@RequestMapping("/rest")
public class SpotifyRestController {

    /* The Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyRestController.class);

    /* Autowire Spotify service */
    @Autowired
    private ISpotifyService spotifyService;

    /* Autowire Preview service */
    @Autowired
    private IPreviewService previewService;

    /**
     * Searches for a song in the Spotify Web API <em>search for an item</em> endpoint and returns
     * 30 seconds long preview of the song.
     *
     * @param songname Name of the song to get the preview url
     * @param token Access token for the Spotify Web API
     * @return If successful returns the audio preview, else returns error description.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/songfinder")
    public ResponseEntity findSongAndGetPreview(
            @RequestParam(name = "songname", defaultValue = "") String songname,
            @RequestParam(name = "token", defaultValue = "") String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        /* Validate the query parameters */
        if (StringUtils.isEmpty(token)) {
            LOGGER.error("InvalidRequest - Token is empty!");
            return new ResponseEntity<>(
                    new SongFinderError(HttpStatus.BAD_REQUEST,"Token cannot be empty"), headers,
                    HttpStatus.BAD_REQUEST);
        } else if (StringUtils.isEmpty(songname)) {
            LOGGER.error("InvalidRequest - Songname is empty!");
            return new ResponseEntity<>(
                    new SongFinderError(HttpStatus.BAD_REQUEST,"Songname cannot be empty"), headers,
                    HttpStatus.BAD_REQUEST);
        }

        /* Search Spotify for the song */
        SearchItemInput input = new SearchItemInput(songname, token);
        LOGGER.debug("Asking Spotify Web API for the song[" + songname + "] with token[" + token + "].");
        SearchItemOutput output = spotifyService.searchItem(input);

        if (output.getStatus() == HttpStatus.OK.value()) {
            LOGGER.debug("Successfully retrieved song[" + songname + "]'s preview url. - Response: "
                    + output.getPreviewUrl());

            /* Song is found! Get raw audio. */
            PreviewInput previewInput = new PreviewInput(output.getPreviewUrl());
            LOGGER.debug("Asking raw audio for the retrieved song.");
            PreviewOutput previewOutput = previewService.getSongPreview(previewInput);
            if (previewOutput.getStatus() == HttpStatus.OK.value()) {
                /* Audio is successfully retrieved, return raw audio with audio/mpeg Content-Type header */
                LOGGER.debug("Successfully retrieved raw audio for the song. Content Length["
                        + previewOutput.getRawAudio().length + "].");

                headers.set("Content-Type", "audio/mpeg");
                headers.set("Content-Length", new Integer(previewOutput.getRawAudio().length).toString());
                headers.set("Accept-Ranges", "bytes");

                return new ResponseEntity<>(previewOutput.getRawAudio(), headers, HttpStatus.OK);
            } else {
                /* Audio cannot be retrieved, return error. */
                HttpStatus status = HttpStatus.resolve(previewOutput.getStatus());
                if (status == null) {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
                LOGGER.error("Cannot retrieve raw audio for preview url[" + output.getPreviewUrl() + "]. Status["
                        + status + "] - Error Message: " + previewOutput.getErrorMessage() + ".");
                return new ResponseEntity<>(
                        new SongFinderError(status, previewOutput.getErrorMessage()), headers, status);
            }
        } else {
            /* Song is not found, return error. */
            HttpStatus status = HttpStatus.resolve(output.getStatus());
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            LOGGER.error("Cannot retrieve song[" + songname + "]'s preview url. Status[" + status
                    + "] - Error Message: " + output.getErrorMessage() + ".");
            return new ResponseEntity<>(new SongFinderError(status, output.getErrorMessage()), headers, status);
        }
    }
}
