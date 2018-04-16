package com.utkuyavuz.songfinder.service.contract;

import com.utkuyavuz.songfinder.service.input.PreviewInput;
import com.utkuyavuz.songfinder.service.output.PreviewOutput;

/**
 * Service interface for preview operations
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public interface IPreviewService {

    /**
     * Gets audio preview or operation error for specified URL
     *
     * @param input The getSongPreview's input {@link PreviewInput}
     * @return A {@link PreviewOutput} object containing the status code and the service output or error description
     */
    public PreviewOutput getSongPreview(PreviewInput input);
}
