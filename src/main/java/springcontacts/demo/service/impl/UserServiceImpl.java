package springcontacts.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springcontacts.demo.entity.Role;
import springcontacts.demo.entity.Status;
import springcontacts.demo.entity.User;
import springcontacts.demo.repo.RoleRepo;
import springcontacts.demo.repo.UserRepo;
import springcontacts.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registration(User user) {
        Role roleUser;
        if (userRepo.findCount()<1)
            roleUser = roleRepo.findByName("ROLE_ADMIN");
        else
            roleUser = roleRepo.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registredUser = userRepo.save(user);
        log.info("IN Register - user : {} successfully registred ", registredUser.getUsername());
        return registredUser;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepo.findByUsername(username);
        //log.warn("IN findByUsername -user found by username: {}  ", username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepo.findById(id).orElse(null);
        if (result==null){
            //log.warn("IN findById - not user found by id: {}  ", id);
            return null;
        }
        log.warn("IN findById -user found by id: {}  ", result);
        return result;
    }
}
