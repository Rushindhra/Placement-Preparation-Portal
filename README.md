# Placement Preparation Portal

A Java JSP/Servlet web application for placement preparation. It provides user registration/login, subject-wise preparation topics, task tracking, mock tests, test results, job listings, and admin management screens.

## Tech Stack

- Java Servlets and JSP
- Apache Tomcat 9
- MySQL
- JDBC with MySQL Connector/J
- BCrypt password hashing with jBCrypt
- HTML, CSS, and JavaScript

## Project Structure

```text
PlacementPortal/
|-- css/                 # Application styles
|-- js/                  # Shared JSP/JavaScript fragments
|-- pages/
|   |-- admin/           # Admin JSP pages
|   `-- user/            # User JSP pages
|-- sql/
|   `-- schema.sql       # MySQL schema and seed data
|-- WEB-INF/
|   |-- classes/         # Java source/classes
|   |-- lib/             # Required JAR files
|   `-- web.xml          # Web application configuration
|-- build.ps1            # Java compilation script
`-- index.jsp
```

## Database Setup

The database script is available at:

```text
sql/schema.sql
```

It creates the MySQL database named `placement_portal`, creates all required tables, and inserts sample data for admins, subjects, topics, mock tests, questions, and jobs.

### Import the Schema

Using MySQL command line:

```bash
mysql -u root -p < sql/schema.sql
```

Or open `sql/schema.sql` in MySQL Workbench and run the full script.

## Database Tables

The schema creates these main tables:

- `users` - registered student/user accounts
- `admins` - administrator accounts
- `subjects` - preparation subject categories
- `topics` - study topics linked to subjects
- `user_topic_progress` - per-user topic completion status
- `tasks` - user to-do list items
- `mock_tests` - mock test definitions
- `questions` - MCQ questions for mock tests
- `test_results` - submitted test scores
- `jobs` - placement/job opportunities
- `job_applications` - user applications for jobs

## Seed Data

The schema inserts:

- One default admin account
- Six preparation subjects
- Sample topics for aptitude, DSA, algorithms, and core CS
- Three mock tests
- Sample MCQ questions
- Sample job listings

Default admin login:

```text
Username: admin
Password: Admin@123
```

## Database Connection

The application connects to MySQL through:

```text
WEB-INF/classes/com/placement/utils/DBConnection.java
```

Default connection settings in the code:

```text
Database: placement_portal
Host: localhost
Port: 3306
User: root
```

Update the MySQL password in `DBConnection.java` if your local MySQL password is different.

## Build and Run

1. Place the project inside Tomcat's `webapps` directory.
2. Import the database using `sql/schema.sql`.
3. Ensure these libraries exist in `WEB-INF/lib`:
   - MySQL Connector/J
   - jBCrypt
4. Compile the Java files:

```powershell
.\build.ps1
```

5. Restart Tomcat.
6. Open the application:

```text
http://localhost:8080/PlacementPortal/
```

## Main Routes

- User login: `/PlacementPortal/login`
- User dashboard: `/PlacementPortal/dashboard`
- Admin login: `/PlacementPortal/admin/login`
- Admin dashboard: `/PlacementPortal/admin/dashboard`

## Notes

- Passwords are stored as BCrypt hashes.
- Foreign keys use cascading deletes where child records should be removed with their parent records.
- The sample data is intended for development and testing.
- Review seeded job deadlines and sample external links before using the data in a live environment.
