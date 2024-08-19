package com.example.test.best_travel.util.exceptions;

public class ForbiddenCustomerException extends RuntimeException{
 
    public ForbiddenCustomerException(){
        super("This customer is blocked.");
    }
    
}
