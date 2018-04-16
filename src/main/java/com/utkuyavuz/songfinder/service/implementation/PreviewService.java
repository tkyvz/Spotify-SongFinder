package com.utkuyavuz.songfinder.service.implementation;

import com.utkuyavuz.songfinder.service.contract.IPreviewService;
import com.utkuyavuz.songfinder.service.input.PreviewInput;
import com.utkuyavuz.songfinder.service.output.PreviewOutput;
import com.utkuyavuz.songfinder.util.RestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * Implementation for preview operations
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 * @see IPreviewService
 */
@Service
public class PreviewService implements IPreviewService {

    /**
     * {@inheritDoc}
     */
    @Override
    public PreviewOutput getSongPreview(PreviewInput input) {
        PreviewOutput output = new PreviewOutput();

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

        /* Send HTTP GET request to the preview url */
        RestUtils restUtils = new RestUtils(input.getPreviewUrl());
        restUtils
                .addHeader("Accept", MediaType.ALL_VALUE)
                .get();

        if (restUtils.isSuccess()) {
            /* Audio retrieved, return the raw audio */
            output.setRawAudio(restUtils.getRawResponse());
            output.setStatus(HttpStatus.OK.value());
        } else {
            /* Audio cannot be retrieved, return the error detail */
            output.setErrorMessage(restUtils.getErrorMessage());
            output.setStatus(restUtils.getStatusCode().value());
        }

        return output;
    }
}
