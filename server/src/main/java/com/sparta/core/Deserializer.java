package com.sparta.core;

public interface Deserializer<T> {

    T deserialize();

    void initialize(String providerName, byte[] content);
}
