package cn.leonma.zofi.account.service;

/**
 * Created by Leon on 17/4/18.
 */
import cn.leonma.zofi.account.domain.Account;
import cn.leonma.zofi.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthAccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accounts;
    private User userdetails;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        Account account = getUserDetail(username);
        if (account != null) {
            userdetails = new User(account.getUsername(),
                    account.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
//                    getAuthorities(account.getRole())
                    //TODO: 实现了角色后替换这里
                    getAuthorities(1)
            );
            return userdetails;
        } else {
            userdetails = new User("empty",
                    "empty",
                    false,
                    true,
                    true,
                    false,
                    getAuthorities(1)
            );
            return userdetails;
        }
    }

    public List<GrantedAuthority> getAuthorities(Integer role) {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        if (role.intValue() == 1) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (role.intValue() == 2) {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authList;
    }

    private Account getUserDetail(String username) {

        Account account = accounts.findByUserName(username);
        if (account == null) {
            System.out.println("account '" + username + "' on null!");
        } else {
            System.out.println(account.toString());
        }

        return account;
    }
}