package com.utkuyavuz.songfinder.service.input;

import org.springframework.util.StringUtils;

/**
 * Input class for {@link com.utkuyavuz.songfinder.service.contract.IPreviewService#getSongPreview(PreviewInput)}
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public class PreviewInput {

    /** Preview Url */
    private String previewUrl;

    /**
     * The default constructor
     */
    public PreviewInput() {

    }

    /**
     * The constructor with field
     *
     * @param previewUrl Preview url to fetch raw audio as {@link String}
     */
    public PreviewInput(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    /**
     * Gets the preview url to fetch raw audio
     *
     * @return The preview url whose raw audio will be fetched
     */
    public String getPreviewUrl() {
        return previewUrl;
    }

    /**
     * Sets the preview url to fetch raw audio
     *
     * @param previewUrl The preview url whose raw audio will be fetched
     */
    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    /**
     * Validates the instance for
     * {@link com.utkuyavuz.songfinder.service.contract.IPreviewService#getSongPreview(PreviewInput)} method
     *
     * @return Returns <code>true</code> if {@link #previewUrl} is not empty, else <code>false</code>
     */
    public boolean isValid() {
        if (StringUtils.isEmpty(previewUrl)) {
            return false;
        }
        return true;
    }
}
