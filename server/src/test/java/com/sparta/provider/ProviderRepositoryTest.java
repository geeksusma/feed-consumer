package com.sparta.provider;

import com.sparta.provider.load.Provider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class ProviderRepositoryTest {

    private ProviderRepository providerRepository;

    @Mock
    private Map<String, Provider> providerDataSource;

    @Before
    public void setUp() {
        providerRepository = new ProviderRepository(providerDataSource);
    }

    @Test
    public void should_appendProviderById_when_save() {
        //given
        final Provider provider = Mockito.mock(Provider.class);
        given(provider.getId()).willReturn("cepsa");

        //when
        providerRepository.save(provider);

        //then
        then(providerDataSource).should().put("cepsa", provider);
    }

    @Test
    public void should_returnEmpty_when_providerDoesNotExist() {

        //when
        final Optional<Provider> providerData = providerRepository.byId("repsol");

        //then
        assertThat(providerData).isEmpty();
    }

    @Test
    public void should_returnProviderData_when_exists() {
        //given
        final Provider expectedData = Mockito.mock(Provider.class);
        given(providerDataSource.containsKey("bp")).willReturn(true);
        given(providerDataSource.get("bp")).willReturn(expectedData);

        //when
        final Optional<Provider> providerData = providerRepository.byId("bp");

        //then
        assertThat(providerData.orElseThrow(IllegalArgumentException::new)).isEqualTo(expectedData);
    }
}