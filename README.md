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
- CRUD operations tested with Postman using generated mock data
- Integrated the rest of the database tables into the existing architecture
- Written table-specific queries and additional service methods
- Revised the user, lecturer, and student structure to align with the database design
- Code optimization and minor refactorings implemented for a cleaner structure
- Security features (authentication & authorization) implemented (JWT integrated)

---

## In Progress

- Connect the frontend application to the backend API
- Deploying the application to virtual private server
- Setting up CI/CD pipelines for automated testing and deployment
- Adding unit tests, integration tests, and security tests
- Enhancing logging and monitoring mechanisms
- Performance optimizations and codebase refactoring
- Optimizing database queries and indices


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