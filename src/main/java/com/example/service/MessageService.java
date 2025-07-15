package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.*; 

@Service
public class MessageService {

    MessageRepository messageRepository;
    private List<Message> messageList = new ArrayList<>();

    @Autowired
    public MessageService(MessageRepository messagerepository){
        
        this.messageRepository = messagerepository;
    }


    //3. Create New Message
    public Message createMessage(Message message){

        // The creation of the message will be successful if and only if the message_text is not blank,
        // is under 255 characters, and posted_by refers to a real, existing user. 
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 254){
            return null;
        }

        //Checking if message refers to a real, existing user     
        for(Message m : messageList){
            if(m.getPostedBy() == message.getPostedBy()){}
            else{
                return null;
            }   
        }

        messageList.add(message);
        return messageRepository.save(message);
    }


    //4. Get All Messages
    public List<Message> getAllMessages(){
        
        return messageRepository.findAll();
    }

    
    //5. Get One Message Given Message Id
    public Message getMessageById(int messageId){
        
        return messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found with ID: " + messageId));  
    }


    //6. Delete a Message Given Message Id
    public void deleteMessageById(int messageId){

        Message messageToDelete = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found with ID: " + messageId));
        
        messageList.remove(messageToDelete);
        messageRepository.delete(messageToDelete);
    }


    //7. Update Message Given Message Id
    public Message updateMessageById(int messageId, Message newMessage){

        Message existingMessage = getMessageById(messageId);
        existingMessage.setMessageId(newMessage.getMessageId());
        existingMessage.setPostedBy(newMessage.getPostedBy());
        existingMessage.setMessageText(newMessage.getMessageText());
        existingMessage.setTimePostedEpoch(newMessage.getTimePostedEpoch());
        
        return messageRepository.save(existingMessage);
    }


    //8. Get All Messages From User Given Account Id
    public List<Message> findAllMessagesById(int accountId){

        return messageRepository.findAllById(accountId);
       
    }

}
