package com.utkuyavuz.songfinder.model;

import org.springframework.http.HttpStatus;

/**
 * The class to return error information for the Web API request.
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public class SongFinderError {

    /* Error detail */
    private String errorMessage;
    /* Http status code */
    private int statusCode;

    /**
     * The default constructor
     */
    public SongFinderError() {

    }

    /**
     * The constructor with fields
     *
     * @param statusCode HttpStatus code as {@link Integer}
     * @param errorMessage ErrorMessage as {@link String}
     */
    public SongFinderError(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    /**
     * The constructor with fields, get the value for the <code>HttpStatus</code>
     *
     * @param statusCode HttpStatus code as {@link HttpStatus}
     * @param errorMessage ErrorMessage as {@link String}
     */
    public SongFinderError(HttpStatus statusCode, String errorMessage) {
        this.statusCode = statusCode.value();
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the error message for the request
     *
     * @return The error message of the Web API request
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the error message for the request
     *
     * @param errorMessage The error message for the request
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the status code for the request
     *
     * @return The status code of the Web API request
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code for the request
     *
     * @param statusCode The status code for the request
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
