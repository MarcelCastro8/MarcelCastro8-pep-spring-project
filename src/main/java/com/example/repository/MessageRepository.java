package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    //Custom query method using the "postedBy" field in Message entity.  
    List<Message> findAllByPostedBy(int postedBy);

}
