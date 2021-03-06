package com.philaporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Philip Porter
 */
@RestController
public class TransactionController {
    
    @PostMapping("/receive")
    public ResponseEntity saveData(@RequestBody String json) {
        System.out.println("Received:");
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Transaction trans = null;
        try {
            trans = objectMapper.readValue(json, Transaction.class);
        } catch (IOException ex) {
            System.err.println("Couldn't map the JSON to the Transaction.class attached to micro-service 2");
        }
        if(trans != null){
            trans.setFinale(Integer.toString(trans.toString().hashCode()));
        }
        // ResponseEntity(T body, MultiValueMap<java.lang.String,java.lang.String> headers, HttpStatus status)
        // Create a new HttpEntity with the given body, headers, and status code.
        return new ResponseEntity((trans != null) ? trans.toString() : "{}", HttpStatus.OK);
    }
}
