package cn.leonma.zofi.account.service;

import cn.leonma.zofi.account.domain.Account;
import cn.leonma.zofi.account.domain.AccountGroup;
import cn.leonma.zofi.account.repository.AccountGroupRepository;
import cn.leonma.zofi.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by Leon on 17/4/16.
 */

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountGroupRepository accountGroupRepository;

    @Autowired
    private AccountRepository accountRepository;



    @Override
    public Account createAccount(Account account) {
        Account existing = null;
        try{
            existing = accountRepository.findByUserName(account.getUsername());
        } catch (NullPointerException e) {
            log.warn("account account already exists: " + account.getUsername());
        }
        Assert.isNull(existing, "account account already exists: " + account.getUsername());
        String hash = encoder.encode(account.getPassword());
        account.setPassword(hash);

        account.setCreatedOn(new Date());
        accountRepository.save(account);
        log.info("new account user has been created: " + account.getUsername());
        return account;
    }

    @Override
    public Account findAccountByName(String userName) {
        Assert.hasLength(userName);
        return accountRepository.findByUserName(userName);
    }

    @Override
    public void saveAccountChanges(String userName, Account account) {
        Account existing = accountRepository.findByUserName(userName);
        Assert.notNull(existing, "can`t find account user: " + userName);

        if(null != account.getEmail() && !account.getEmail().isEmpty()){
            existing.setEmail(account.getEmail());
        }
        if(null != account.getName() && !account.getName().isEmpty()){
            existing.setName(account.getName());
        }
        if(null != account.getIdNumber() && !account.getIdNumber().isEmpty()){
            existing.setIdNumber(account.getIdNumber());
        }
        if(null != account.getPassword() && !account.getPassword().isEmpty()){
            existing.setPassword(encoder.encode(account.getPassword()));
        }
        if(null != account.getPhone() && !account.getPhone().isEmpty()){
            existing.setPhone(account.getPhone());
        }
        if(null != account.getSex() && !account.getSex().isEmpty()){
            existing.setSex(account.getSex());
        }
        existing.setUpdatedOn(new Date());

        log.debug("user account {} changes has been updated", existing.getUsername());

        accountRepository.save(existing);
    }

    @Override
    public void updateAccountState(String userName, Integer newState) {
        Account existing = accountRepository.findByUserName(userName);
        Assert.notNull(existing, "can`t find account user: " + userName);

        if(null != newState){
            existing.setState(newState);
            accountRepository.save(existing);
            log.debug("user account {} changes state {} has been updated", newState);
        }
    }

    @Override
    public void withdrawAccount(String userName) {
        Account existing = accountRepository.findByUserName(userName);
        Assert.notNull(existing, "can`t find account user: " + userName);
        accountRepository.delete(existing);
    }

    @Override
    public void resetPassword(String userName, String password) {
        Account existing = accountRepository.findByUserName(userName);
        Assert.notNull(existing, "can`t find account user: " + userName);
        if(null != password && !encoder.matches(password, existing.getPassword())){
            existing.setPassword(encoder.encode(password));
            accountRepository.save(existing);
            log.info("user account {} changes password {} has been updated");
        }
    }

    @Override
    public List<Account> getAllAccountsOfGroup(String groupName) {
        AccountGroup existing = accountGroupRepository.findByGroupName(groupName);
        Assert.notNull(existing, "can`t find group: " + groupName);

        return existing.getAccounts();

    }
}
