package com.example.service;

import java.util.*;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;
    private List<Account> accountList = new ArrayList<>();
    

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    
    //1. User Registration
    public void register(Account account){

        //The registration will be successful if and only if the username is not blank, the password is
        //at least 4 characters long, and an Account with that username does not already exist. 
        if(account==null || accountList.contains(account) || account.getPassword().length() < 4) {
            return;}
        
        accountList.add(account);
        accountRepository.save(account);
    }

     
    //2. Login
    public void login(String username, String password) throws AuthenticationException {

        //The login will be successful if and only if the username and password provided in the request body JSON
        //match a real account existing on the database.
        for(Account acc : accountList){
            if(acc.getUsername().equals(username) && acc.getPassword().equals(password)){
                return;
            }
        }
        throw new AuthenticationException("Login unsuccesfull. Your username and password are invalid."
         + " Please check and try again!");

    }

}
