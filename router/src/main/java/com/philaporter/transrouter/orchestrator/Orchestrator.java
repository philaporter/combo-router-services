package com.philaporter.transrouter.orchestrator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Philip Porter
 */
public class Orchestrator {
    
    // Could very easily pull from / save to a table in a relational db
    // This internal object structure using maps could still be viable, but we would
    // want to include a step at the point of initialization to take all the details 
    // from the relational database and create the maps. then we would want to update
    // database everytime a TR was told to update its port details. Otherwise, the 
    // regular processing would use the in memory objects and not have to use the db
    private Map<String, Boolean> listOfPorts = new HashMap<>();
    private String hostname;
    
    // Initialize the OrchestratorDetails object with tracking details
    public Orchestrator(String hostname, Map listOfPorts) {
        this.hostname = hostname;
        this.listOfPorts = listOfPorts;
    }

    // To pull details from the Orchestrator's map
    public boolean getPortDetailsFromMap(String key){
        return this.listOfPorts.get(key);
    }
    
    // To update the port details for an Orchestrator's map
    public void setPortDetailsIntoMap(String key, boolean active){
        this.listOfPorts.put(key, active);
    }
    
    public Map<String, Boolean> getMap() {
        return listOfPorts;
    }
    
    public String getHostname() {
        return this.hostname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Hostname: "+ hostname + "\n");
        listOfPorts.entrySet().forEach((entry) -> {
            sb.append(entry.getKey()).append(": ")
                    .append(entry.getValue()).append("\n");
        });
        return sb.toString();
    }
}
