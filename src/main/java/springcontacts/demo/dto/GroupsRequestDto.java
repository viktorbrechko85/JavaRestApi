package springcontacts.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import springcontacts.demo.entity.GroupContacts;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupsRequestDto {
    private String name;

    public GroupContacts toGroupContacts(){
        GroupContacts groupContacts = new GroupContacts();
        groupContacts.setName(name);
        return groupContacts;
    }

    public static GroupsRequestDto fromGroupContacts(GroupContacts groupContacts){
        GroupsRequestDto groupsRequestDto = new GroupsRequestDto();
        groupsRequestDto.setName(groupContacts.getName());
        return groupsRequestDto;
    }
}
