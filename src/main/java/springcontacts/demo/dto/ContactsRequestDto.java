package springcontacts.demo.dto;

import lombok.Data;
import springcontacts.demo.entity.Contact;

@Data
public class ContactsRequestDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact toContact(){
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhone(phone);
        contact.setAddress(address);

        return contact;
    }

    public static ContactsRequestDto fromContact(Contact contact){
        ContactsRequestDto contactsRequestDto = new ContactsRequestDto();
        contactsRequestDto.setFirstName(contact.getFirstName());
        contactsRequestDto.setLastName(contact.getLastName());
        contactsRequestDto.setPhone(contact.getPhone());
        contactsRequestDto.setAddress(contact.getAddress());
        return contactsRequestDto;
    }
}
