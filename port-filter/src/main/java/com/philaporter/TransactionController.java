package com.philaporter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Philip Porter
 */
@RestController
public class TransactionController {
    
    @GetMapping("/1111")
    public ResponseEntity respond1111() {
        System.out.println("Executed the 1111 path");
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @GetMapping("/2222")
    public ResponseEntity respond2222() {
        System.out.println("Executed the 2222 path");
        return new ResponseEntity(HttpStatus.OK);
    }
}
