package com.sparta.provider.load;

import com.sparta.core.Deserializer;
import com.sparta.provider.SaveProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class LoadServiceImplTest {

    private Load loadService;

    @Mock
    private SaveProvider saveProvider;

    @Mock
    private Deserializer<Provider> deserializer;

    @Before
    public void setUp() {
        loadService = new LoadServiceImpl(saveProvider, deserializer);
    }

    @Test
    public void should_initialize_when_load() {
        //given
        final Provider provider = Mockito.mock(Provider.class);
        given(deserializer.deserialize()).willReturn(provider);

        //when
        loadService.loadProviderData("cepsa", "cepsa".getBytes());

        //then
        then(deserializer).should().initialize("cepsa", "cepsa".getBytes());
    }

    @Test
    public void should_saveProvider_when_deserialized() {
        //given
        final Provider provider = Mockito.mock(Provider.class);
        given(deserializer.deserialize()).willReturn(provider);

        //when
        loadService.loadProviderData("cepsa", "cepsa".getBytes());

        //then
        then(saveProvider).should().save(provider);
    }

    @Test
    public void should_returnRecords_when_saved() {
        //given
        final Provider provider = Mockito.mock(Provider.class);

        given(provider.getTotal()).willReturn(123L);
        given(deserializer.deserialize()).willReturn(provider);

        //when
        final long totalRecords = loadService.loadProviderData("cepsa", "cepsa".getBytes());

        //then
        assertThat(totalRecords).isEqualTo(provider.getTotal());
    }
}