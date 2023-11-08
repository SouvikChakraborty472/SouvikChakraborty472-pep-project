package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message createMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        String insertQuery = "INSERT INTO messages (message_text, posted_by) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message.getPosted_by());
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int message_id = generatedKeys.getInt(1);
                    message.setMessage_id(message_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public List<Message> getAllMessage(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> message = new ArrayList<>();
        try {
            String sql = "SELECT id, name FROM author";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message messages = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                message.add(messages);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    public Message getMessageWithId(Message message){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM messages WHERE message_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, message.getMessage_id());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create a Message object from the retrieved data
                int id = resultSet.getInt("message_id");
                int postedby = resultSet.getInt("posted_by");
                String text = resultSet.getString("message_text");
                Long time = resultSet.getLong("time_posted_epoch");

                return new Message(id, postedby, text, time);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
        return null;
    }

    public boolean deleteMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        String deleteQuery = "DELETE FROM messages WHERE message_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, message.getMessage_id());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOldMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "UPDATE messages SET message_text = ? WHERE message_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, message.getMessage_text());
            stmt.setInt(2, message.getMessage_id());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.message_id, m.message_text, m.posted_by, m.time_posted_epoch " + "FROM message m " + "WHERE m.posted_by = ?";

        try (
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

}
