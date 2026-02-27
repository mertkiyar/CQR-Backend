package com.mrtkyr.classqroom.entity;

import com.mrtkyr.classqroom.enums.AssessmentType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeId {
    private UUID studentId;
    private UUID courseId;
    private AssessmentType assessmentType;
}
