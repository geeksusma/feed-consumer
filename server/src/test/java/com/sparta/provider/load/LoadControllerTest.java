package com.sparta.provider.load;

import com.sparta.core.ChecksumFailedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class LoadControllerTest {

    private LoadController loadController;
    @Mock
    private Load loader;


    @Before
    public void setUp() {
        loadController = new LoadController(loader);
    }

    @Test
    public void should_returnOk_when_loaded() {

        //when
        final ResponseEntity<Long> cepsa = loadController.load("cepsa", "cepsa".getBytes());

        //then
        assertThat(cepsa.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void should_loadProvider_when_load() {
        //given
        final String provider = "bp";

        //when
        loadController.load(provider, provider.getBytes());

        //then
        then(loader).should().loadProviderData(provider, provider.getBytes());
    }

    @Test
    public void should_returnAmountOfRecords_when_providerLoadedSuccessfully() {
        //given
        final String provider = "shell";
        final long totalOfRecords = 1234213L;
        given(loader.loadProviderData(provider, provider.getBytes())).willReturn(totalOfRecords);

        //when
        final ResponseEntity<Long> response = loadController.load(provider, provider.getBytes());

        //then
        assertThat(response.getBody()).isEqualTo(totalOfRecords);
    }

    @Test
    public void should_returnUnprocessableEntity_when_checksumsFailed() {
        //given
        final String provider = "repsol";

        given(loader.loadProviderData(provider, provider.getBytes())).willThrow(new ChecksumFailedException("failed"));

        //when
        final ResponseEntity<Long> response = loadController.load(provider, provider.getBytes());

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }


}