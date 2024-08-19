package com.example.test.best_travel.infrastructure.helpers;

import org.springframework.stereotype.Component;

import com.example.test.best_travel.util.exceptions.ForbiddenCustomerException;

@Component
public class BlackListHelper {
    
    public void isInBlackListCustomer(String customerId){
        if(customerId.equals("BBMB771012HMCRR022")){    
            throw new ForbiddenCustomerException();
        }
    }

}
