package de.pearlbay.stockaireference.application;

public class EmployeeNotFoundException extends RuntimeException {
    
    private long id;
    
    public EmployeeNotFoundException(long id) {
        super("Could not find employee " + id);
    }
}
