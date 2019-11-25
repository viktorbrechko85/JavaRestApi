package springcontacts.demo.service;

import springcontacts.demo.entity.Contact;
import springcontacts.demo.entity.User;

import java.util.List;

public interface ContactsService {
    Contact create(Contact contact, User user);
    void addContactToGroup(Long contact_id, Long grp_id);
    List<Contact> findByUser(User user);
    Contact findByfullname(String name);
}
