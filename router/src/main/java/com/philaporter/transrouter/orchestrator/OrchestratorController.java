package com.philaporter.transrouter.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philaporter.transrouter.Application;
import java.io.IOException;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Philip Porter
 */
public class OrchestratorController {
    
    @GetMapping("/getCurrentSubscriptions")
    public Map<String, Orchestrator> detailOrchestratorSubscriptions() {
        return Application.orchMap;
    }
    
    @PostMapping("/updateSubscription")
    public void toggleSubscriptionStatus(@RequestBody String json) {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        OrchestratorSubDetail orc = null;
        try {
            orc = objectMapper.readValue(json, OrchestratorSubDetail.class);
            System.out.println(orc.getHostname());
        } catch (IOException ex) {
            System.err.println("Couldn't map the JSON to the Orchestrator.class within the Transaction Router stub");
        }
        if(orc != null){
            Orchestrator od = Application.orchMap.get(orc.getHostname());
            od.setPortDetailsIntoMap(orc.getPort(), !od.getPortDetailsFromMap(orc.getPort()));
        }
    }
    
    public static void listAvailableOrchestrators(String port) {
        System.out.println();
        for(Map.Entry<String, Orchestrator> entry: Application.orchMap.entrySet()){
            if(entry.getValue().getPortDetailsFromMap(port)){
                System.out.println("Available Orchestrator for " + port + ": " + entry.getValue().getHostname());
            }
        }
    }
}
