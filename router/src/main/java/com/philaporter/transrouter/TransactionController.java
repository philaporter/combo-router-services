package com.philaporter.transrouter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Philip Porter
 */
@RestController
public class TransactionController {
    
    private List<Transaction> list;
    // this could be static
    private HashMap<String, OrchestratorDetails> orchMap;
    private URL obj;
    private HttpURLConnection con;
    private Map<String, String> map = new HashMap<>();
    
    @GetMapping("/send") // /send?destination=http://localhost:8080/api/workflow/transaction-workflow-port-filter
    public Transaction trigger(@RequestParam String destination) {
        Random random = new Random();
        int index = random.nextInt(list.size() - 1);
        
        // Load balancing could use something like this method to see which Orchestrators are available
        listAvailableOrchestrators(list.get(index).getPort());
        postTransaction(list.get(index), destination);
        return list.get(index);
    }
    
    public void listAvailableOrchestrators(String port) {
        for(Map.Entry<String, OrchestratorDetails> entry: orchMap.entrySet()){
            if(entry.getValue().getPortDetailsFromMap(port)){
                System.out.println("Available Orchestrator for " + port + ": " + entry.getValue().getHostname());
            }
        }
    }
    
    @GetMapping("/getCurrentSubscriptions")
    public Map<String, OrchestratorDetails> detailOrchestratorSubscriptions() {
        return orchMap;
    }
    
    @PostMapping("/updateSubscription")
    public void toggleSubscriptionStatus(@RequestBody String json) {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Orchestrator orc = null;
        try {
            orc = objectMapper.readValue(json, Orchestrator.class);
            System.out.println(orc.getHostname());
        } catch (IOException ex) {
            System.err.println("Couldn't map the JSON to the Orchestrator.class within the Transaction Router stub");
        }
        if(orc != null){
            OrchestratorDetails od = orchMap.get(orc.getHostname());
            od.setPortDetailsIntoMap(orc.getPort(), !od.getPortDetailsFromMap(orc.getPort()));
        }
    }
    
    private void postTransaction(Transaction trans, String url) {
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
        } catch (MalformedURLException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.disconnect();
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
        orchMap.put("http://localhost:8080/", new OrchestratorDetails("http://localhost:8080/", map1));
        orchMap.put("http://server-a:8080/", new OrchestratorDetails("http://server-a:8080/", map2));
        orchMap.put("http://server-b:8080/", new OrchestratorDetails("http://server-b:8080/", map3));
//        
        System.out.println("======================================================");
        System.out.println(">>>> > Establishing Sample Orchestrator Details < <<<<");
        System.out.println("======================================================");
        for(Map.Entry<String, OrchestratorDetails> entry : orchMap.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }
    }
}
