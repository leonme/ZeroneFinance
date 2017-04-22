package cn.leonma.zofi.account.service;

import cn.leonma.zofi.account.domain.Account;
import cn.leonma.zofi.account.domain.AccountGroup;

import java.math.BigDecimal;

/**
 * Created by Leon on 17/4/19.
 */
public interface AccountGroupService {
    /**
     * Checks if group with the same name already exists
     * Creates new group with default parameters
     * @param group
     * @return created account
     */
    AccountGroup createAccountGroup(AccountGroup group);

    /**
     * Find account group by given name
     * @param groupName
     * @return found group
     */
    AccountGroup findAccountGroupByName(String groupName);

    /**
     * Validates and applies incoming group updates
     * @param groupName
     * @param group
     */
    void saveGroupChanges(String groupName, AccountGroup group);

    /**
     * Validate and update budget for specified user group
     * @param groupName
     * @param newBudget
     */
    void updateGroupBudget(String groupName, BigDecimal newBudget);

    /**
     * Validate and update Threshold for specified user group
     * @param groupName
     * @param newThreshold
     */
    void updateGroupThreshold(String groupName, BigDecimal newThreshold);

    /**
     * Add a new member to the specified user group
     * @param groupName
     * @param newAccount
     */
    void addNewAccountToGroup(String groupName, Account newAccount);
}
