package com.mrtkyr.classqroom.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerCourseId implements Serializable {
    private UUID lecturerId;
    private UUID courseId;
}
