package springcontacts.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import springcontacts.demo.dto.GroupsRequestDto;
import springcontacts.demo.entity.GroupContacts;
import springcontacts.demo.entity.User;
import springcontacts.demo.security.jwt.JwtTokenProvider;
import springcontacts.demo.service.GroupContactsService;
import springcontacts.demo.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
public class GroupsRestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final GroupContactsService groupContactsService;

    @Autowired
    public GroupsRestController(JwtTokenProvider jwtTokenProvider, UserService userService, GroupContactsService groupContactsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.groupContactsService = groupContactsService;
    }

    @RequestMapping(value="/groups", method = RequestMethod.GET)
    public ResponseEntity groups(@AuthenticationPrincipal UserDetails userDetails, @RequestHeader(name="Authorization") String token){
        User user = userService.findByUsername(userDetails.getUsername());
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("groups", groupContactsService.findByUser(user)
                .stream()
                .map(a -> "Id:" + a.getId()+". " + a.getName())
                .collect(toList())
        );
        return  ResponseEntity.ok(model);
    }

    @RequestMapping(value="/groups", method = RequestMethod.POST)
    public ResponseEntity groups(@AuthenticationPrincipal UserDetails userDetails, @RequestBody GroupsRequestDto requestDto, @RequestHeader(name="Authorization") String token){
        try{
            String username = userDetails.getUsername();//jwtTokenProvider.getUsername(token);
            User user = userService.findByUsername(username);
            String groupName = requestDto.getName();

            if (user==null){
                throw new UsernameNotFoundException("User with username " + username + " not founded");
            }

            GroupContacts result = groupContactsService.create(new GroupContacts(groupName),user);


            Map<Object, Object> response = new HashMap<>();
            response.put("groupname", result.getName());
            response.put("user", user.getUsername());

            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password!");
        }

    }
}
