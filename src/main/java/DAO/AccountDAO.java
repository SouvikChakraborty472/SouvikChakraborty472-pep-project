package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;


public class AccountDAO {

    public Account verifyTheAccount(Account account){
        Connection con=ConnectionUtil.getConnection();
        try {
            String sql="select * from account where username=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,account.getUsername());
            ResultSet  rs=ps.executeQuery();
            while(rs.next())
            {
                    Account acc=new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                    return acc;
           }
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
          return null;
}
    public Account createAccount(Account account){
        Connection con=ConnectionUtil.getConnection();
        try{
            String sql="INSERT INTO account (username,password) VALUES (?,?)";
            PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,account.getUsername());
            ps.setString(2,account.getPassword());
            ps.executeUpdate();
            ResultSet keyrs=ps.getGeneratedKeys();
                  if(keyrs.next()){
                int generated_account_key=(int) keyrs.getInt("account_id");
                return new Account(generated_account_key,account.getUsername(),account.getPassword());
                 }
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Account getTheAccountByUsername(String name){
        Connection con=ConnectionUtil.getConnection();
        try {
            String sql="select * from account where username=(?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
               Account account=new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                return account;
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage()); 
       }
        return null;
    }
    
    public Account getAccountByUser_Id(int id){
        Connection con=ConnectionUtil.getConnection();
        try {
            String sql="select * from account where username=(?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
               Account account=new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                return account;
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage()); 
       }
        return null;
    }
        
}       


