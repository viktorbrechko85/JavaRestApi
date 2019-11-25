package springcontacts.demo.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import springcontacts.demo.entity.Role;
import springcontacts.demo.entity.Status;
import springcontacts.demo.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user){
        return new JwtUser(
              user.getFirstName(),
              user.getLastName(),
              user.getUsername(),
              user.getPassword(),
              user.getStatus().equals(Status.ACTIVE),
              user.getUpdated(),
              mapToGrantedAuthorities(new ArrayList<>(user.getRoles())));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles){
        return userRoles.stream()
                .map(role ->
                    new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
