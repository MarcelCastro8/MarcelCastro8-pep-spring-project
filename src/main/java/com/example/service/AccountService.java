package com.example.service;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //1. User Registration
    public Account register(Account account){

        //The registration will be successful if and only if the username is not blank, the password is
        //at least 4 characters long, and an Account with that username does not already exist. 
        if(account == null || account.getUsername() == null || account.getUsername().isBlank() 
                           || account.getPassword() == null || account.getPassword().isBlank() 
                           || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid username or password!");
        }
        
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        
        if(existingAccount != null){
            throw new EntityExistsException("Sorry, but that username already exist.");
        } 

        return accountRepository.save(account);
    }

     
    //2. Login
    public Account login(String username, String password) throws ResourceNotFoundException {

        //The login will be successful if and only if the username and password provided in the request body JSON
        //match a real account existing on the database.
        if(username == null || username.isBlank()
          || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Username and password must not be null or empty.");
        }

       Account account = accountRepository.findByUsername(username);

       if(account != null && account.getPassword().equals(password)){
        return account;
       }else{
            throw new ResourceNotFoundException("Invalid username or password!");
       }
    }
}
