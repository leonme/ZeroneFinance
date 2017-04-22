package cn.leonma.zofi.account.service;

import cn.leonma.zofi.account.domain.Account;

import java.util.List;

/**
 * Created by Leon on 17/4/16.
 */
public interface AccountService {

    /**
     * Checks if user with the same name already exists
     * Creates new user with default parameters
     * @param account
     * @return
     */
    Account createAccount(Account account);

    /**
     * Find user by name
     * @param userName
     * @return
     */
    Account findAccountByName(String userName);

    /**
     * Validates and applies incoming account updates
     * @param userName
     * @param account
     */
    void saveAccountChanges(String userName, Account account);

    /**
     * Update user state
     * @param userName
     * @param newState
     */
    void updateAccountState(String userName, Integer newState);

    /**
     * withdraw user account
     * @param userName
     */
    void withdrawAccount(String userName);

    void resetPassword(String userName, String password);

    List<Account> getAllAccountsOfGroup(String groupName);
}
