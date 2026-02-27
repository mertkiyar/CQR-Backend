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
public class StudentCourseId implements Serializable {
    private UUID studentId;
    private UUID courseId;
}
