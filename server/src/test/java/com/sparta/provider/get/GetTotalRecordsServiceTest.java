package com.sparta.provider.get;

import com.sparta.core.ProviderNotFoundException;
import com.sparta.provider.GetProvider;
import com.sparta.provider.load.Provider;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetTotalRecordsServiceTest {

    private GetTotalRecordsService getTotalRecordsService;

    @Mock
    private GetProvider getProvider;

    @Before
    public void setUp() {
        getTotalRecordsService = new GetTotalRecordsService(getProvider);
    }

    @Test
    public void should_throwNotFound_when_providerDoesNotExist() {

        //when

        final Throwable raisedException = Assertions.catchThrowable(() -> getTotalRecordsService.total("shell"));

        //then
        assertThat(raisedException).isInstanceOf(ProviderNotFoundException.class)
                .hasMessage("Not found");
    }

    @Test
    public void should_returnTotal_when_exists() {
        //given
        final Provider dataProvider = Mockito.mock(Provider.class);
        final long total = 12313L;

        given(dataProvider.getTotal()).willReturn(total);
        given(getProvider.byId("shell")).willReturn(Optional.of(dataProvider));

        //when
        final Long totalShell = getTotalRecordsService.total("shell");

        //then
        assertThat(totalShell).isEqualTo(total);
    }
}