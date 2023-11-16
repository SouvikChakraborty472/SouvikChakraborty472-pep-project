package Service;

import Model.Message;

import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

         //GET ALL MESSAGES
      public List<Message> getAllmessages()
    {
         return messageDAO.getAllMessage();
    }
    //CREATE NEW MESSAGES
    public Message addMessage(Message message){
        return messageDAO.createMessage(message);
    }
    //RETRIEVE MESSAGES BY ID
     public Message getMessageById(int id)
     {
        return messageDAO.getTheMessagesById(id);
     }

     //DELETE MESSAGES BY ID
     public Message deleteMessageById(int id)
     {
        return messageDAO.deleteBymessageId(id);
     }

     //UPDATE MASSEGES BY ID
     public Message updateMessage(int id,Message message)
     {
        if(messageDAO.getTheMessagesById(id) != null){
           messageDAO.updateMessageById(id, message);
            return messageDAO.getTheMessagesById(id);
        }
        else return null;
        
     }

     //RETRIEVE ALL MESSAGE BY USER ID
 public List<Message> getMessagesByUserId(int id){
    return messageDAO.getMessagesByUserId(id);
 }
}