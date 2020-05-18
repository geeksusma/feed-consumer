package com.sparta.provider.load;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
class Record {

    private long index;
    private long timestamp;
    private String city;
    private List<Sensor> sensors;

}
