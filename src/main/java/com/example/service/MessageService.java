package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List; 

@Service
public class MessageService {

    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messagerepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messagerepository;
    }


    //3. Create New Message
    public Message createMessage(Message message){

        String text = message.getMessageText();

        // The creation of the message will be successful if and only if the message_text is not blank,
        // is under 255 characters, and posted_by refers to a real, existing user. 
        if(text == null || text.isBlank() || text.length() > 254){
            throw new IllegalArgumentException("Message text is invalid");
        }
        
        //Checking that posted_by refers to a real, existing user.
        if(!accountRepository.existsById(message.getPostedBy())){
            throw new IllegalArgumentException("Message field postedBy doesn't refer to a valid account.");
        }

        return messageRepository.save(message);
    }


    //4. Get All Messages
    public List<Message> getAllMessages(){  
        return messageRepository.findAll();
    }

    
    //5. Get One Message Given Message Id
    public Message getMessageById(int messageId){
        
        return messageRepository.findById(messageId)
            .orElseThrow(() -> new ResourceNotFoundException("Message not found with ID: " + messageId));  
    }


    //6. Delete a Message Given Message Id
    public void deleteMessageById(int messageId){

        Message messageToDelete = messageRepository.findById(messageId)
            .orElseThrow(() -> new ResourceNotFoundException("Message not found with ID: " + messageId));
        
        messageRepository.delete(messageToDelete);
    }


    //7. Update Message Given Message Id
    public Message updateMessageById(int messageId, Message newMessage){

        Message existingMessage = getMessageById(messageId);
       
        String newMessageText = newMessage.getMessageText();
        if(newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 254){
            throw new IllegalArgumentException("Message text is invalid");

        }

        existingMessage.setMessageText(newMessageText);
        existingMessage.setTimePostedEpoch(newMessage.getTimePostedEpoch());
        
        return messageRepository.save(existingMessage);
    }


    //8. Get All Messages From User Given Account Id
    public List<Message> findAllMessagesByAccountId(int accID){
        return messageRepository.findAllByPostedBy(accID);
    }
}
