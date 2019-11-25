package springcontacts.demo.rest;

import org.omg.CORBA.NO_IMPLEMENT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import springcontacts.demo.dto.AuthenticationRequestDto;
import springcontacts.demo.entity.User;
import springcontacts.demo.security.jwt.JwtTokenProvider;
import springcontacts.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
public class UsersRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public UsersRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }
    @RequestMapping(value="/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
        try{
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user==null){
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password!");
        }

    }

    @RequestMapping(value="/users")
    public ResponseEntity users(@RequestBody AuthenticationRequestDto requestDto){
        try{
            String username = requestDto.getUsername();

            User user = userService.findByUsername(username);
            Map<Object, Object> response = new HashMap<>();
            if (user!=null){
                throw new UsernameNotFoundException("User with username " + username + " already exists");
                //System.out.println("User with username " + username + " already exists");
                //return ResponseEntity.ok(response);
            }
            User newUser = userService.registration(new User(username, requestDto.getPassword()));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            String token = jwtTokenProvider.createToken(username, newUser.getRoles());

            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password!");
        }

    }

    @RequestMapping(value="/status", method = RequestMethod.GET)
    @ResponseBody
    public String status(HttpServletRequest request, @RequestHeader (name="Authorization") String token) {
        Principal principal = request.getUserPrincipal();
        return "Username = " + principal.getName();
    }

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );
        return ResponseEntity.ok(model);
    }

}
