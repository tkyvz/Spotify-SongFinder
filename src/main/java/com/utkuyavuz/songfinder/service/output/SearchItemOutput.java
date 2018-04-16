package com.utkuyavuz.songfinder.service.output;

import com.utkuyavuz.songfinder.service.input.SearchItemInput;

/**
 * Output class for {@link com.utkuyavuz.songfinder.service.contract.ISpotifyService#searchItem(SearchItemInput)}
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public class SearchItemOutput {

    /** Error Message */
    private String errorMessage;
    /** The preview Url */
    private String previewUrl;
    /** Http Status */
    private int status;

    /**
     * Gets the error message for the <code>searchItem</code> method.
     *
     * @return The error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message for the <code>searchItem</code> method.
     *
     * @param errorMessage The error message as {@link String}
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the preview url for the <code>searchItem</code> method.
     *
     * @return The preview url
     */
    public String getPreviewUrl() {
        return previewUrl;
    }

    /**
     * Sets the preview url for the <code>searchItem</code> method.
     *
     * @param previewUrl The preview url as {@link String}
     */
    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    /**
     * Gets the HttpStatus for the <code>searchItem</code> method.
     *
     * @return The HttpStatus
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the HttpStatus for the <code>searchItem</code> method.
     *
     * @param status The HttpStatus as {@link Integer}
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
