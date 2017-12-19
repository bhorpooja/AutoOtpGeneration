package com.codekul.AutoSmsIntegration.repo;

import com.codekul.AutoSmsIntegration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by pooja on 15/12/17.
 */
public interface UserRepo extends MongoRepository<User,String> {

    List<User> findAll();

    User findByMobileNo(String mobileNo);
}
