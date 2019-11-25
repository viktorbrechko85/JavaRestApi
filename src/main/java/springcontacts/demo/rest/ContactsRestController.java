package springcontacts.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import springcontacts.demo.dto.ContactsRequestDto;
import springcontacts.demo.dto.GroupsRequestDto;
import springcontacts.demo.entity.Contact;
import springcontacts.demo.entity.GroupContacts;
import springcontacts.demo.entity.User;
import springcontacts.demo.security.jwt.JwtTokenProvider;
import springcontacts.demo.service.ContactsService;
import springcontacts.demo.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
public class ContactsRestController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final ContactsService contactsService;

    @Autowired
    public ContactsRestController(JwtTokenProvider jwtTokenProvider, UserService userService, ContactsService contactsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.contactsService = contactsService;
    }
    @RequestMapping(value="/contacts", method = RequestMethod.GET)
    public ResponseEntity groups(@AuthenticationPrincipal UserDetails userDetails, @RequestHeader(name="Authorization") String token){
        User user = userService.findByUsername(userDetails.getUsername());
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("contacts", contactsService.findByUser(user)
                .stream()
                .map(a -> "Id:" + a.getId()+". " + a.getFirstName()+" " + a.getLastName() + ". Phone: " + a.getPhone())
                .collect(toList())
        );
        return  ResponseEntity.ok(model);
    }

    @RequestMapping(value="/contacts", method = RequestMethod.POST)
    public ResponseEntity contacts(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ContactsRequestDto requestDto, @RequestHeader(name="Authorization") String token){
        try{
            String username = userDetails.getUsername();
            User user = userService.findByUsername(username);
            String firstName = requestDto.getFirstName();
            String lastName = requestDto.getLastName();
            String phone = requestDto.getPhone();
            String address = requestDto.getAddress();

            if (user==null){
                throw new UsernameNotFoundException("User with username " + username + " not founded");
            }

            Contact contact = new Contact(firstName, lastName);
            contact.setPhone(phone);
            contact.setAddress(address);
            Contact result = contactsService.create(contact, user);


            Map<Object, Object> response = new HashMap<>();
            response.put("Fullname", result.getFirstName() + " " + result.getLastName());
            response.put("phone", result.getPhone());
            response.put("address", result.getAddress());
            response.put("user", user.getUsername());

            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password!");
        }

    }
}
