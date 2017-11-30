package com.philaporter.transrouter;

/**
 * @author Philip Porter
 */
public class Orchestrator {
    
    private String hostname;
    private String port;
    
    public Orchestrator() {
        
    }

    public Orchestrator(String hostname, String port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
