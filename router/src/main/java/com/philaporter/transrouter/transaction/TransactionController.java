package com.philaporter.transrouter.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philaporter.transrouter.Application;
import com.philaporter.transrouter.orchestrator.OrchestratorController;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Philip Porter
 */
@RestController
public class TransactionController {
    
    private URL obj;
    private HttpURLConnection con;
    private Map<String, String> map = new HashMap<>();
    
    @GetMapping("/send") // /send?destination=http://localhost:8080/api/workflow/transaction-workflow-port-filter
    public Transaction trigger(@RequestParam String destination) {
        Random random = new Random();
        int index = random.nextInt(Application.list.size() - 1);
        
        // Load balancing could use something like this method to see which Orchestrators are available
        OrchestratorController.listAvailableOrchestrators(Application.list.get(index).getPort());
        // TO INSERT THE CONDUCTOR-HOSTNAMES for postTransaction
        
        postTransaction(Application.list.get(index), destination);
        return Application.list.get(index);
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
}
