package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Attendance;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAttendanceSessionIU {

    @NotNull(message = "Attendance cannot be null!")
    private Attendance attendance;

    @NotNull(message = "Six Digit Code cannot be null!")
    @Size(min = 6, max = 6, message = "Six Digit Code cannot be lower or upper than 6 chars!")
    private String sixDigitCode;

    @NotNull(message = "Create Time cannot be null!")
    private LocalDateTime createdAt;

    @NotNull(message = "Expire Time cannot be null!")
    private LocalDateTime expiresAt;

    @NotNull(message = "Active variable cannot be null!")
    private boolean active;
}
