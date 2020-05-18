package com.sparta.provider;

import com.sparta.provider.load.Provider;

import java.util.Optional;

public interface GetProvider {

    Optional<Provider> byId(String id);
}
