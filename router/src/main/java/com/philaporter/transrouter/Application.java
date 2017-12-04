package com.philaporter.transrouter;

import com.philaporter.transrouter.orchestrator.Orchestrator;
import com.philaporter.transrouter.transaction.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    public static HashMap<String, Orchestrator> orchMap = new HashMap<>();;
    
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
        
        // DB pull 
        
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
//        orchMap = new HashMap<>();
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/OrchestratorDB",
              "philip", "abc123");
           stmt = c.createStatement();
           String sql = "SELECT hostname, port, active FROM orch_hosts, orch_ports WHERE orch_ports.id = orch_hosts.id;";
           rs = stmt.executeQuery(sql);
           while(rs.next()){
             int port = rs.getInt("port");
             String hostname = rs.getString("hostname");
             boolean active = rs.getBoolean("active");
             HashMap<String, Boolean> map = new HashMap<>();
             map.put(Integer.toString(port), active);
             if(rs.next()){
                port = rs.getInt("port");
                hostname = rs.getString("hostname");
                active = rs.getBoolean("active");
                map.put(Integer.toString(port), active);
             }
             orchMap.put(hostname, new Orchestrator(hostname, map));
//             System.out.println(map.toString());
           }
         rs.close();
         stmt.close();
         c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
        System.out.println("======================================================");
        System.out.println(">>>> >> >>  Created baseline from database  << << <<<<");
        System.out.println("======================================================");
        
        // Old stuff
//        HashMap<String, Boolean> map1 = new HashMap<>();
//        map1.put("1111", Boolean.TRUE);
//        map1.put("2222", Boolean.TRUE);
//        
//        HashMap<String, Boolean> map2 = new HashMap<>();
//        map2.put("1111", Boolean.FALSE);
//        map2.put("2222", Boolean.TRUE);
//        
//        HashMap<String, Boolean> map3 = new HashMap<>();
//        map3.put("1111", Boolean.TRUE);
//        map3.put("2222", Boolean.FALSE);
        
        // Setting up the intitial Orchestrator publishing details
//        orchMap = new HashMap<>();
//        orchMap.put("http://localhost:8080/", new Orchestrator("http://localhost:8080/", map1));
//        orchMap.put("http://server-a:8080/", new Orchestrator("http://server-a:8080/", map2));
//        orchMap.put("http://server-b:8080/", new Orchestrator("http://server-b:8080/", map3));
        
//        System.out.println("======================================================");
//        System.out.println(">>>> > Establishing Sample Orchestrator Details < <<<<");
//        System.out.println("======================================================");
        for(Map.Entry<String, Orchestrator> entry : orchMap.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }
    }
}
