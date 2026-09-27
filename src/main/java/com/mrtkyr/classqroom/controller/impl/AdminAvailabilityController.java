package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.entity.RootEntity;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminAvailabilityController {
    private final JdbcTemplate jdbc;

    public AdminAvailabilityController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/admin/availability")
    public RootEntity<Map<String, Boolean>> availability() {
        return RootEntity.ok(jdbc.queryForObject("""
                SELECT EXISTS (SELECT 1 FROM languages) AS languages,
                       EXISTS (SELECT 1 FROM faculties) AS faculties,
                       EXISTS (SELECT 1 FROM departments) AS departments,
                       EXISTS (SELECT 1 FROM lecturers) AS lecturers,
                       EXISTS (SELECT 1 FROM students) AS students,
                       EXISTS (SELECT 1 FROM courses) AS courses,
                       EXISTS (SELECT 1 FROM attendances) AS attendances
                """, (rs, row) -> Map.of(
                "languages", rs.getBoolean("languages"),
                "faculties", rs.getBoolean("faculties"),
                "departments", rs.getBoolean("departments"),
                "lecturers", rs.getBoolean("lecturers"),
                "students", rs.getBoolean("students"),
                "courses", rs.getBoolean("courses"),
                "attendances", rs.getBoolean("attendances"))));
    }
}
