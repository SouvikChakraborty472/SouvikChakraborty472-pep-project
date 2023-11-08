package Service;

import Model.Message;

import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    private static MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        if (messageDAO.createMessage(message) != null) {
            return messageDAO.getMessageWithId(message);
        }
        return null;
    }

    public List<Message> getAllMessage() {
            return messageDAO.getAllMessage();

    }

    public Message getMessageFromDatabase(Message message) {
        return messageDAO.getMessageWithId(message);
    }

    public Message deleteMessage(Message message) {
        if (messageDAO.deleteMessage(message)) {
            return messageDAO.getMessageWithId(message);
        }
        return null; 
    }

    public static Message updateMessage(Message message) {
        if (messageDAO.updateOldMessage(message)) {
            return messageDAO.getMessageWithId(message);
        }
        return null; 
    
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageDAO.getAllMessagesByAccountId(accountId);
    }

}