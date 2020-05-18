package com.sparta.provider.load;

import com.sparta.core.ChecksumFailedException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LoadController {

    private final Load loadService;

    @PostMapping("/load/{provider}")
    public ResponseEntity<Long> load(@PathVariable("provider") String provider, @RequestBody byte[] content) {

        //better if I do this using aspects TODO
        try {
            return ResponseEntity.ok(loadService.loadProviderData(provider, content));
        } catch (ChecksumFailedException e) {
            return ResponseEntity.unprocessableEntity().build();
        }

    }

}
