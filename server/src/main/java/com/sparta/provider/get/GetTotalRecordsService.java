package com.sparta.provider.get;

import com.sparta.core.ProviderNotFoundException;
import com.sparta.provider.GetProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
class GetTotalRecordsService implements GetTotalRecords {

    private final GetProvider getProvider;


    @Override
    public Long total(String provider) {

        return getProvider.byId(provider).orElseThrow(() -> new ProviderNotFoundException("Not found")).getTotal();

    }
}
