package com.philaporter.transrouter;

import com.philaporter.transrouter.orchestrator.Orchestrator;
import com.philaporter.transrouter.transaction.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Philip Porter
 */
@SpringBootApplication
public class Application {
    
    public static List<Transaction> list;
    public static HashMap<String, Orchestrator> orchMap;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
        
        HashMap<String, Boolean> map1 = new HashMap<>();
        map1.put("1111", Boolean.TRUE);
        map1.put("2222", Boolean.TRUE);
        
        HashMap<String, Boolean> map2 = new HashMap<>();
        map2.put("1111", Boolean.FALSE);
        map2.put("2222", Boolean.TRUE);
        
        HashMap<String, Boolean> map3 = new HashMap<>();
        map3.put("1111", Boolean.TRUE);
        map3.put("2222", Boolean.FALSE);
        
        // Setting up the intitial Orchestrator publishing details
        orchMap = new HashMap<>();
        orchMap.put("http://localhost:8080/", new Orchestrator("http://localhost:8080/", map1));
        orchMap.put("http://server-a:8080/", new Orchestrator("http://server-a:8080/", map2));
        orchMap.put("http://server-b:8080/", new Orchestrator("http://server-b:8080/", map3));
        
        System.out.println("======================================================");
        System.out.println(">>>> > Establishing Sample Orchestrator Details < <<<<");
        System.out.println("======================================================");
        for(Map.Entry<String, Orchestrator> entry : orchMap.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }
    }
}
