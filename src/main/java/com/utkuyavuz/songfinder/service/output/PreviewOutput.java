package com.utkuyavuz.songfinder.service.output;

import com.utkuyavuz.songfinder.service.input.PreviewInput;

/**
 * Output class for {@link com.utkuyavuz.songfinder.service.contract.IPreviewService#getSongPreview(PreviewInput)}
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public class PreviewOutput {

    /** Error Message */
    private String errorMessage;
    /** Raw Audio */
    private byte[] rawAudio;
    /** Http Status */
    private int status;

    /**
     * Gets the error message for the <code>getSongPreview</code> method.
     *
     * @return The error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message for the <code>getSongPreview</code> method.
     *
     * @param errorMessage The error message as {@link String}
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the raw audio for the <code>getSongPreview</code> method.
     *
     * @return The raw audio
     */
    public byte[] getRawAudio() {
        return rawAudio;
    }

    /**
     * Sets the raw audio for the <code>getSongPreview</code> method.
     *
     * @param rawAudio The raw audio
     */
    public void setRawAudio(byte[] rawAudio) {
        this.rawAudio = rawAudio;
    }

    /**
     * Gets the HttpStatus for the <code>getSongPreview</code> method.
     *
     * @return The HttpStatus
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the HttpStatus for the <code>getSongPreview</code> method.
     *
     * @param status The HttpStatus as {@link Integer}
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
