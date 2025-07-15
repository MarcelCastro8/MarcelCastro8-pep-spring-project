package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

    //Custom query method using "username" field from Account entity. 
    Account findByUsername(String username);

}
