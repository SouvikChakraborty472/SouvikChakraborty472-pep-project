package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;


public class AccountDAO {

    public boolean checkUsername(String Username)  {
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM users WHERE username = ?", Statement.RETURN_GENERATED_KEYS); 
            stmt.setString(1,Username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }                 

    }

    public Account insertNewAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "INSERT INTO account(username, password) VALUES (?, ?)RETURNING account_id";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int accountId = rs.getInt("account_id");
                return new Account(accountId, account.getUsername(), account.getPassword());
            }     
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return account;
        
                                  
    }

    public Account accountLogin(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        String selectQuery = "SELECT account_id, username FROM accounts WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                
                return new Account(accountId, username, password);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}       


