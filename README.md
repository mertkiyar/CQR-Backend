# ClassQRoom Backend

ClassQRoom Backend is a Spring Boot based REST API that I am developing for my attendance management system.

The main purpose of this project is to manage users, courses, departments, enrollments, attendance sessions and attendance records. PostgreSQL is used as the main database. The database schema is manually designed and business rules are mostly enforced at the database level using constraints and triggers.

---

## Current Status

- Spring Boot project created and configured
- PostgreSQL connection successfully integrated
- Core database schema designed and connected to the project
- Business rules implemented mostly on the database side (constraints & triggers)
- Service layer implemented for main entities (User, Course, Department, etc.)
- Organized and layered package structure created for a cleaner architecture
- Custom global exception handler written to return clearer and more meaningful error responses
- DTO structure implemented to prevent direct entity exposure and hide sensitive/unnecessary fields
- CRUD operations tested with Postman using generated mock data (data can be successfully created, read, updated and deleted)
- Remaining tables are currently being integrated into the system with their service layers

---

## In Progress

- Integrating the rest of the database tables into the existing architecture
- Writing table-specific queries and additional service methods where needed
- Code optimization and small refactorings for cleaner structure
- Revising the user, lecturer and student structure to make it more compatible with the database design
- Adding security features (authentication & authorization)

---

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

---

## Notes

This project is still under development and being improved step by step.  
The README file will be updated as new features are implemented and the system becomes more complete.