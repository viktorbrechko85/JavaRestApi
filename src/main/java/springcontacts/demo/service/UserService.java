package springcontacts.demo.service;

import springcontacts.demo.entity.User;

public interface UserService {
    User registration (User user);

    User findByUsername(String username);

    User findById(Long id);

}
