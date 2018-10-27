package de.pearlbay.stockaireference.application;

public class OrderNotFoundException extends RuntimeException{

    private long id;
    
    public OrderNotFoundException(long id) {
        super("Order not found " + id);
    }
}
