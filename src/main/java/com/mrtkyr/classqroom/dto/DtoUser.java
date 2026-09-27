package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.User;
import com.mrtkyr.classqroom.enums.GenderType;
import com.mrtkyr.classqroom.enums.UserType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private GenderType gender;
    private UserType userType;
    private int departmentId;
    private LocalDateTime createdAt;

    public static DtoUser from(User user) {
        DtoUser dto = new DtoUser();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setUserType(user.getUserType());
        dto.setDepartmentId(user.getDepartment().getId());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
