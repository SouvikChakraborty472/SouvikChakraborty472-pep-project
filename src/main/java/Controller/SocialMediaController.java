package Controller;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::getUpdatedMessage);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountId);
        app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postNewAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        String Username = account.getUsername();
        if (account.getUsername() != null && !account.getUsername().isEmpty() && account.getPassword() != null && account.getPassword().length() >= 4) {
            if (!AccountService.containsKey(Username)) {
                Account newAccount = accountService.createAccount(account);
                context.json(newAccount).status(200);
            }else {
                context.status(400);
            }

        }else {
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account authenticatedAccount = accountService.authenticateAccount(account);

        if (authenticatedAccount != null) {
            context.json(authenticatedAccount).status(200);
        } else {
            context.status(401); 
        }
    }

    private void createMessage(Context context) throws JsonProcessingException {
        try {
            Message message = context.bodyAsClass(Message.class);

        if (message != null && !message.getMessage_text().isEmpty() && message.getMessage_text().length() <= 255 && message.getPosted_by() > 0) {
            Message createdMessage = messageService.createMessage(message);
            context.json(createdMessage).status(200);
        }else {
            context.status(400).result("Invalid message data.");
        }
    } catch (Exception e) {
        e.printStackTrace(); 
        context.status(500).result("Internal Server Error");
    }
    }

    private void getAllMessageHandler(Context ctx) {
        List<Message> message = messageService.getAllMessage();
        ctx.json(message);
    }

    private void getMessageById(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message messagebyid = messageService.getMessageFromDatabase(message);

        if (messagebyid != null) {
            ctx.json(messagebyid).status(200);
        } else {
            ctx.result("{}").contentType("application/json").status(200);
        }
    }

    private void deleteMessageHandler(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message deletedMessage = messageService.deleteMessage(message);

            if (deletedMessage != null) {
                context.json(deletedMessage);
            } else {
                context.status(200); 
            }
    }

    private void  getUpdatedMessage(Context ctx) {
    
        Message message = ctx.bodyAsClass(Message.class);
        //String newMessageText = message.getMessage_text();
        //int messageId = message.getMessage_id();
        

        if (message.getMessage_text() != null && message.getMessage_text().length() <= 255) {
            
            if (MessageService.updateMessage(message) != null) {
                ctx.result("Message updated successfully");
                ctx.status(200);
            } else {
                ctx.result("Message update failed");
                ctx.status(400);
            }
        } else {
            ctx.result("Invalid message text");
            ctx.status(400);
        }
    }

    private void getAllMessagesByAccountId(Context context) {
        Account account = context.bodyAsClass(Account.class);
        int accountId = account.getAccount_id();
        List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
        context.json(messages);
    }

}

    

