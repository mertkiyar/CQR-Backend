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
- Automated deployment to VPS using GitHub Actions (CI/CD pipeline optimized)
- Moved QR session generation entirely to the backend with a 15-second rotation scheduler
- Added /me endpoint for fetching current user profiles via JWT
- Integrated Swagger UI for interactive and comprehensive API documentation
- Implemented robust duplicate email validation and student-specific registration flows


---

## In Progress

- Connect the frontend application to the backend API
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
- Docker & Docker Compose
- Nginx
- GitHub Actions

---

## Notes

### Admin panel (local build)

The small admin UI is served by Spring Boot at `/admin` (production hostname in the
checked-in Nginx config: `api.classqroom.mrtkyr.com`). It uses the existing API and
never connects from the browser to PostgreSQL. Admin login stores a JWT in an
HttpOnly, SameSite=Strict cookie. The cookie is Secure on HTTPS. Android Bearer JWT
requests continue to work. Admin writes from the browser require a same-origin
request. Expired sessions return HTTP 401 and the UI asks for login again.

To create the first dedicated administrator, register a **new dedicated lecturer
account** with the existing `/register` flow, then promote only that account once
in PostgreSQL (replace the email with the exact new account email):

```sql
BEGIN;
UPDATE users SET user_type = 'ADMIN'::user_type
WHERE user_email = 'your-dedicated-admin@example.com' AND user_type = 'LECTURER';
-- Verify exactly one row was updated before committing.
COMMIT;
```

Do not promote an Android account you still use as a lecturer. The admin panel
does not offer a way to create or promote ADMIN users.

Before deployment, configure HTTPS between the public edge and the origin as well
as HTTPS for visitors. The checked-in Compose/Nginx setup currently exposes only
HTTP port 80; TLS certificates and origin termination are environment-specific.
The origin reverse proxy must pass its actual HTTPS scheme as `X-Forwarded-Proto`
and preserve `Host`. Admin login rejects non-HTTPS requests outside localhost.
Verify the DNS record, `/admin` route, login cookie flags and a rejected
non-admin request after deployment. Pushing this repository to `main` or `master`
triggers the existing deployment workflow automatically; review these steps first.

This project is still under development and being improved step by step.  
The README file will be updated as new features are implemented and the system becomes more complete.
