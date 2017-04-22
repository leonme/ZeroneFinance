package cn.leonma.zofi.account.service;

import cn.leonma.zofi.account.domain.Account;
import cn.leonma.zofi.account.domain.AccountGroup;
import cn.leonma.zofi.account.repository.AccountGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Leon on 17/4/19.
 */
@Service
public class AccountGroupServiceImpl implements AccountGroupService{

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountGroupRepository accountGroupRepository;

    @Override
    public AccountGroup createAccountGroup(AccountGroup group) {
        AccountGroup existing = accountGroupRepository.findByGroupName(group.getGroupName());
        Assert.isNull(existing, "account group already exists: " + group.getGroupName());
        group.setCreatedOn(new Date());
        accountGroupRepository.save(group);
        log.info("new account group has been created: " + group.getGroupName());
        return group;
    }

    @Override
    public AccountGroup findAccountGroupByName(String groupName) {
        Assert.hasLength(groupName);
        return accountGroupRepository.findByGroupName(groupName);
    }

    @Override
    public void saveGroupChanges(String groupName, AccountGroup group) {
        AccountGroup existing = accountGroupRepository.findByGroupName(groupName);
        Assert.notNull(existing, "can`t find group: " + groupName);

        existing.setBudget(group.getBudget());
        existing.setThreshold(group.getThreshold());
        existing.setAccounts(group.getAccounts());
        existing.setUpdatedOn(new Date());

        log.debug("group {} changes has been saved", group.getGroupName());

        accountGroupRepository.save(group);
    }

    @Override
    public void updateGroupBudget(String groupName, BigDecimal newBudget) {
        AccountGroup existing = accountGroupRepository.findByGroupName(groupName);
        Assert.notNull(existing, "can`t find group: " + groupName);

        existing.setBudget(newBudget);
        existing.setUpdatedOn(new Date());
        log.debug("budget of group {} changes has been saved", existing.getGroupName());

        accountGroupRepository.save(existing);
    }

    @Override
    public void updateGroupThreshold(String groupName, BigDecimal newThreshold) {
        AccountGroup existing = accountGroupRepository.findByGroupName(groupName);
        Assert.notNull(existing, "can`t find group: " + groupName);

        existing.setThreshold(newThreshold);
        existing.setUpdatedOn(new Date());
        log.debug("threshold of group {} changes has been saved", existing.getGroupName());

        accountGroupRepository.save(existing);
    }

    @Override
    public void addNewAccountToGroup(String groupName, Account newAccount) {
        AccountGroup existing = accountGroupRepository.findByGroupName(groupName);
        Assert.notNull(existing, "can`t find group: " + groupName);
        List<Account> newAccounts = existing.getAccounts();
        newAccounts.add(newAccount);
        existing.setAccounts(newAccounts);
        existing.setUpdatedOn(new Date());
        log.debug("new account {} has been added to group {} ", newAccount.getName(), groupName);

        accountGroupRepository.save(existing);
    }
}
