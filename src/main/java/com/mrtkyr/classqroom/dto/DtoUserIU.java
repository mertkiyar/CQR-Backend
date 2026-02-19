package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserIU {
    @NotNull(message = "User Type cannot be null!")
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
