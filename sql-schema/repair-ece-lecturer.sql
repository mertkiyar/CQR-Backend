-- Run once in pgAdmin against the existing classqroom database.
-- Earlier /register versions created a USERS row without a LECTURERS row.
-- Keep the existing user ID, password, and course assignments.
INSERT INTO lecturers (lecturer_id, lecturer_title, lecturer_role)
SELECT u.user_id, 'LECTURER'::academic_title, 'LECTURER'::academic_role
FROM users u
WHERE u.user_email = 'ecedemir@cqr.edu'
  AND u.user_type = 'LECTURER'
  AND NOT EXISTS (
      SELECT 1 FROM lecturers l WHERE l.lecturer_id = u.user_id
  );

SELECT u.user_id, u.user_email, l.lecturer_id IS NOT NULL AS has_lecturer_profile
FROM users u
LEFT JOIN lecturers l ON l.lecturer_id = u.user_id
WHERE u.user_email = 'ecedemir@cqr.edu';
