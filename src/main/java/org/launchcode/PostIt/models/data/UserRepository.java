package org.launchcode.PostIt.models.data;

import org.launchcode.PostIt.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    //findByUserName, returnUser, input username
    User findByUsername(String username);

}