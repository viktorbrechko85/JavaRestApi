package springcontacts.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springcontacts.demo.entity.GroupContacts;
import springcontacts.demo.entity.Role;
import springcontacts.demo.entity.User;
import springcontacts.demo.repo.GroupContactsRepo;
import springcontacts.demo.service.GroupContactsService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupContactsServiceImpl implements GroupContactsService {

    private final GroupContactsRepo groupContactsRepo;

    @Autowired
    public GroupContactsServiceImpl(GroupContactsRepo groupContactsRepo) {
        this.groupContactsRepo = groupContactsRepo;
    }

    @Override
    public GroupContacts create(GroupContacts groupContacts, User user) {
        groupContacts.setUser(user);
        GroupContacts result = groupContactsRepo.save(groupContacts);
        return result;
    }

    @Override
    public List<GroupContacts> findByUser(User user) {
        List<Role> roleList = user.getRoles();
        boolean admin = false;
        for(Role role:roleList){
            if (role.getName().equals("ROLE_ADMIN")) {
                admin = true;
                break;
            }
        }
        List<GroupContacts> result = new ArrayList<>();
        result = admin ? groupContactsRepo.findAll() : groupContactsRepo.findByUser(user);
        return result;
    }

    @Override
    public void deleteallbyUser(User user) {

    }

    @Override
    public List<GroupContacts> findAll() {
        return null;
    }
}
