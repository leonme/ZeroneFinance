package cn.leonma.zofi.account.repository;

import cn.leonma.zofi.account.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Leon on 17/4/16.
 */

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByUserName(String name);
}
