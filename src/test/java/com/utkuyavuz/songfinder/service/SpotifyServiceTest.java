package com.utkuyavuz.songfinder.service;

import com.utkuyavuz.songfinder.service.contract.ISpotifyService;
import com.utkuyavuz.songfinder.service.implementation.SpotifyService;
import com.utkuyavuz.songfinder.service.input.SearchItemInput;
import com.utkuyavuz.songfinder.service.output.SearchItemOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpotifyService.class)
public class SpotifyServiceTest {

    @Autowired
    private ISpotifyService spotifyService;

    @Test
    public void searchItemTest() {

        /* Search Input for Bodrum Akşamları by Bülent Serttaş :) */
        /* Don't forget to replace the token */
        SearchItemInput input = new SearchItemInput(
                "Bodrum Akşamları",
                "BQAkS6Eo-dvuVFJRKcat5Ot5P8FRlkAgYD9-s8q5mWb1saxl1OAQZr4CAguhcbTUIwOE-gGu9Tan-SCvVaRs6Cdx4MP8fpFrAvSdT-IcmRI2FaiKkj0HAG5akQTyiqwBaNTZSwZETG0TCC9RjsF14XJTDp8");

        SearchItemOutput output = spotifyService.searchItem(input);

        /* Output should not be null */
        assert (output != null);
        /* Output StatusCode should be 200 */
        assert (output.getStatus() == HttpStatus.OK.value());
        /* Output errorMessage should be empty */
        assert (output.getErrorMessage() == null);
        /* Output previewUrl should not be empty */
        assert (output.getPreviewUrl() != null);

    }
}
