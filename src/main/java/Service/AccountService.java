package Service;

import Model.Account;
import DAO.AccountDAO;



public class AccountService {
    AccountDAO accountDAO;

public AccountService(){
    accountDAO = new AccountDAO();
}

public AccountService(AccountDAO accountDAO){
    this.accountDAO = accountDAO;
}

public Account addAccount(Account account)
{
     return accountDAO.createAccount(account);
}


public Account verifyLogin(Account account)
{
  return accountDAO.verifyTheAccount(account);
}

//GET THE ACCOUNT BY USERNAME FOR VERIFYING 
public Account getUserByUserName(Account account)
{
  return accountDAO.getTheAccountByUsername(account.getUsername());
}
 //GET THE ACCOUNT BY USER_ID FOR VERIFING
public Account getUserByUserId(Account account)
{
  return accountDAO.getAccountByUser_Id(account.getAccount_id());
}



}