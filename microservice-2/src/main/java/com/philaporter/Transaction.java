package com.philaporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;

/**
 * @author Philip Porter
 */
public class Transaction {
    
    private String port;
    private String type;
    private String message;
    private String trailer;
    
    // add to the message
    private String finale;

    public Transaction() {
    }

    public Transaction(String port, String type, String message, String trailer) {
        this.port = port;
        this.type = type;
        this.message = message;
        this.trailer = trailer;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getFinale() {
        return finale;
    }

    public void setFinale(String finale) {
        this.finale = finale;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.port);
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.message);
        hash = 97 * hash + Objects.hashCode(this.trailer);
        hash = 97 * hash + Objects.hashCode(this.finale);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (!Objects.equals(this.port, other.port)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.trailer, other.trailer)) {
            return false;
        }
        if (!Objects.equals(this.finale, other.finale)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String temp = "";
        try {
            temp = mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            System.err.println("toString() broke? Really?");
        }
        return temp;
    }
}
