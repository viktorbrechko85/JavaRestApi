package springcontacts.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springcontacts.demo.entity.Contact;
import springcontacts.demo.entity.GroupContacts;
import springcontacts.demo.entity.Role;
import springcontacts.demo.entity.User;
import springcontacts.demo.repo.ContactsRepo;
import springcontacts.demo.repo.GroupContactsRepo;
import springcontacts.demo.service.ContactsService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {
    private final ContactsRepo contactsRepo;
    private final GroupContactsRepo groupContactsRepo;

    @Autowired
    public ContactsServiceImpl(ContactsRepo contactsRepo, GroupContactsRepo groupContactsRepo) {
        this.contactsRepo = contactsRepo;
        this.groupContactsRepo = groupContactsRepo;
    }

    @Override
    public Contact create(Contact contact, User user) {
        contact.setUser(user);
        Contact result = contactsRepo.save(contact);
        return result;
    }

    @Override
    public void addContactToGroup(Long contact_id, Long grp_id) {

    }

    @Override
    public List<Contact> findByUser(User user) {
        List<Role> roleList = user.getRoles();
        boolean admin = false;
        for(Role role:roleList){
            if (role.getName().equals("ROLE_ADMIN")) {
                admin = true;
                break;
            }
        }
        List<Contact> result = new ArrayList<>();
       result = admin ? contactsRepo.findAll() : contactsRepo.findByUser(user);
        return result;
    }

    @Override
    public Contact findByfullname(String name) {
        return null;
    }
}
