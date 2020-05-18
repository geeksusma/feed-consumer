package com.sparta.provider.load;

import com.sparta.core.Deserializer;
import com.sparta.provider.SaveProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
class LoadServiceImpl implements Load {

    private final SaveProvider saveProvider;

    private final Deserializer<Provider> providerDeserializer;


    @Override
    public long loadProviderData(String provider, byte[] data) {
        providerDeserializer.initialize(provider, data);
        final Provider dataProvider = providerDeserializer.deserialize();
        saveProvider.save(dataProvider);
        return dataProvider.getTotal();
    }
}
