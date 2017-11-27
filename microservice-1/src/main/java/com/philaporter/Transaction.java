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
    
    // add to the message
    private String trailer;

    public Transaction() {
    }

    public Transaction(String port, String type, String message) {
        this.port = port;
        this.type = type;
        this.message = message;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.port);
        hash = 83 * hash + Objects.hashCode(this.type);
        hash = 83 * hash + Objects.hashCode(this.message);
        hash = 83 * hash + Objects.hashCode(this.trailer);
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
