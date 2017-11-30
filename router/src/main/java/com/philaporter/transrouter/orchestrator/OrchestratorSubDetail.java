package com.philaporter.transrouter.orchestrator;

/**
 * @author Philip Porter
 */
public class OrchestratorSubDetail {
    
    private String hostname;
    private String port;
    
    public OrchestratorSubDetail() {
        
    }

    public OrchestratorSubDetail(String hostname, String port) {
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
