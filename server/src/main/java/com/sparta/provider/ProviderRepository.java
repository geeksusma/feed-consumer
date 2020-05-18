package com.sparta.provider;

import com.sparta.provider.load.Provider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
class ProviderRepository implements SaveProvider, GetProvider {

    private final Map<String, Provider> providerDataSource;

    @Override
    public void save(Provider provider) {
        providerDataSource.put(provider.getId(), provider);
    }

    @Override
    public Optional<Provider> byId(String id) {
        if (providerDataSource.containsKey(id)) {
            return Optional.of(providerDataSource.get(id));
        }
        return Optional.empty();
    }
}
