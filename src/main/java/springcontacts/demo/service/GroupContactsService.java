package springcontacts.demo.service;

import springcontacts.demo.entity.GroupContacts;
import springcontacts.demo.entity.User;

import java.util.List;

public interface GroupContactsService {
    GroupContacts create(GroupContacts groupContacts, User user);

    List<GroupContacts> findByUser(User user);

    void deleteallbyUser(User user);

    List<GroupContacts> findAll();
}
