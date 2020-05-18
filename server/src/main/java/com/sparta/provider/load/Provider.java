package com.sparta.provider.load;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Provider {

    private String id;
    private long total;
    private List<Record> records;
}
