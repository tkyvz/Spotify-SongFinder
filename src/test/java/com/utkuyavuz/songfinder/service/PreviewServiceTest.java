package com.utkuyavuz.songfinder.service;

import com.utkuyavuz.songfinder.service.contract.IPreviewService;
import com.utkuyavuz.songfinder.service.implementation.PreviewService;
import com.utkuyavuz.songfinder.service.input.PreviewInput;
import com.utkuyavuz.songfinder.service.output.PreviewOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreviewService.class)
public class PreviewServiceTest {

    @Autowired
    private IPreviewService previewService;

    @Test
    public void getPreviewData() {
        /* Preview input for Bodrum Akşamları by Bülent Serttaş :) */
        PreviewInput input = new PreviewInput("https://p.scdn.co/mp3-preview/47ae160853bef11a71e109da384440a29e5d56b0?cid=774b29d4f13844c495f206cafdad9c86");

        PreviewOutput output = previewService.getSongPreview(input);

        /* Output should not be null */
        assert (output != null);
        /* Output StatusCode should be 200 */
        assert (output.getStatus() == HttpStatus.OK.value());
        /* Output errorMessage should be empty */
        assert (output.getErrorMessage() == null);
        /* Output response should have length > 0 */
        assert (output.getRawAudio().length > 0);
    }
}
