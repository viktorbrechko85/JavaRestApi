package springcontacts.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import springcontacts.demo.entity.Status;
import springcontacts.demo.entity.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoRequestDto {
    private long id;
    private String username;
    private String status;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setStatus(Status.valueOf(status));
        return user;
    }

    public static UserInfoRequestDto fromUser(User user){
        UserInfoRequestDto userInfoRequestDto = new UserInfoRequestDto();
        userInfoRequestDto.setId(user.getId());
        userInfoRequestDto.setUsername(user.getUsername());
        userInfoRequestDto.setStatus(user.getStatus().name());
        return userInfoRequestDto;
    }
}
