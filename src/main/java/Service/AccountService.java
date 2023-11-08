package Service;

import Model.Account;
import DAO.AccountDAO;



public class AccountService {
    private static AccountDAO accountDAO;

public AccountService(){
    accountDAO = new AccountDAO();
}

public AccountService(AccountDAO accountDAO){
    this.accountDAO = accountDAO;
}

public static boolean containsKey(String Username) {
    return accountDAO.checkUsername(Username);

}

public Account createAccount(Account account) {
    return accountDAO.insertNewAccount(account);
}

public Account authenticateAccount(Account account) {
    if (account == null || account.getUsername() == null || account.getPassword() == null) {
        return null;
    }
    
    Account authenticaAccount = accountDAO.accountLogin(account);
    return authenticaAccount;
}

}