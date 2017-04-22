package cn.leonma.zofi.account.controller;

import cn.leonma.zofi.account.domain.Account;
import cn.leonma.zofi.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Leon on 17/4/16.
 */

@RestController
@RequestMapping("/account")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * get user account by user name
     * @param name
     * @return
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Account getAccountByName(@PathVariable String name){
        return accountService.findAccountByName(name);
    }

    /**
     * create a new user account
     * @param account
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Account createNewAccount(@Valid @RequestBody Account account) {
        return accountService.createAccount(account);
    }

    /**
     * get all accounts of the given account group
     * @param groupName
     * @return
     */
    @RequestMapping(value="/all/{groupName}", method = RequestMethod.GET)
    public List<Account> getAllAccountsOfGroup(@PathVariable String groupName) {
        return accountService.getAllAccountsOfGroup(groupName);
    }

    /**
     * update and save account changes
     * @param name
     * @param account
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public void saveAccountChanges(@PathVariable String name, @Valid @RequestBody Account account){
        accountService.saveAccountChanges(name, account);
    }

    /**
     * delete a user account(withdraw)
     * @param name
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public void withdrawAccount(@PathVariable String name){
        accountService.withdrawAccount(name);
    }

    @RequestMapping(value = "/updateState/{name}/{state}", method = RequestMethod.PUT)
    public void updateState(@PathVariable String name, @PathVariable Integer state){
        accountService.updateAccountState(name, state);
    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.PUT)
    public void resetPwd(@RequestParam(value = "name", required = true) String name,
                         @RequestParam(value = "pwd", required = true) String pwd){
        accountService.resetPassword(name, pwd);
    }


}
