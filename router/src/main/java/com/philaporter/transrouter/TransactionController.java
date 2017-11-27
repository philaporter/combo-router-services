package com.philaporter.transrouter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Philip Porter
 */
@RestController
public class TransactionController {
    
    private List<Transaction> list;
    
    @GetMapping("/send") // /send?destination=http://localhost:8080/api/workflow/transaction-workflow
    public Transaction trigger(@RequestParam String destination) {
        Random random = new Random();
        int index = random.nextInt(list.size() - 1);
        postTransaction(list.get(index), destination);
        return list.get(index);
    }
    
    private void postTransaction(Transaction trans, String url) {
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(trans);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(
                    con.getOutputStream()
            );
            wr.writeBytes(body);
            wr.flush(); wr.close();
            System.out.println();
            System.out.println("Destination: " + url);
            System.out.println("Request body: " + body);
            System.out.println("Response Code: " + con.getResponseCode());

//            BufferedReader br = new BufferedReader(
//                new InputStreamReader(
//                    con.getInputStream()
//                )
//            );
//            String input = "";
//            StringBuilder response = new StringBuilder();
//            while(br.ready()) {
//                input = br.readLine();
//                response.append(input);
//            }
//            br.close();
//            System.out.println("Response: " + response.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Setup
    {
        list = new ArrayList<>();
        list.add(new Transaction("1111", "1100", "123message1"));
        list.add(new Transaction("2222", "1200", "234message2"));
        list.add(new Transaction("1111", "1100", "345message3"));
        list.add(new Transaction("2222", "1200", "456message4"));
        list.add(new Transaction("1111", "1100", "567message5"));
        list.add(new Transaction("2222", "1200", "678message6"));
        list.add(new Transaction("1111", "1100", "789message7"));
        list.add(new Transaction("2222", "1200", "890message8"));
        
        System.out.println("======================================================");
        System.out.println(">>>> > >>  Establishing Sample Transactions  << < <<<<");
        System.out.println("======================================================");
        list.stream().forEach((l)->System.out.println(l.toString()));
    }
}
