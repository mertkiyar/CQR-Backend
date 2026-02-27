package com.mrtkyr.classqroom.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerCourseId {
    private UUID lecturerId;
    private UUID courseId;
    private boolean active;
}
