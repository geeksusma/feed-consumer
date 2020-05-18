package com.sparta.provider.get;

import com.sparta.core.ProviderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class GetProviderRecordsController {

    private final GetTotalRecords getTotalRecords;

    @GetMapping("/data/{provider}/total")
    public ResponseEntity<Long> total(@PathVariable("provider") String provider) {

        //I'd prefer to do this exception handling via aspects to don't pollute the controller layer TODO
        try {
            return ResponseEntity.ok(getTotalRecords.total(provider));
        } catch (ProviderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }


}
