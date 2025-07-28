# 📘 Blogify - A Monolithic Blogging Web Application

Blogify is a monolithic web application built using Spring Boot. It offers a full-featured blog platform with authentication, role-based access control, and CRUD functionalities. The application follows a clean MVC architecture and uses the in-memory H2 database for development and testing purposes.

---

## 🧩 Dependencies Used

The project leverages the following dependencies:

- **Spring Boot Starters**:
  - `spring-boot-starter-web` - RESTful web development
  - `spring-boot-starter-data-jpa` - ORM with JPA/Hibernate
  - `spring-boot-starter-thymeleaf` - HTML rendering with Thymeleaf
  - `spring-boot-starter-security` - Secure login & role-based access control
  - `spring-boot-starter-validation` - Bean validation
  - `spring-boot-starter-mail` - Sending emails (optional)
  - `spring-boot-devtools` - Live reloading for development
  - `spring-boot-starter-test` - Unit and integration testing

- **Others**:
  - `com.h2database:h2` - In-memory database
  - `lombok` - Reduces boilerplate (getters, setters, constructors)
  - `commons-lang3` - Utility functions
  - `thymeleaf-extras-springsecurity6` - Security tags in Thymeleaf
  - `thymeleaf-extras-java8time` - Java 8 time formatting in views

---

## 🏛 Architecture

The application follows the **Model-View-Controller (MVC)** pattern:

- **Model**: Contains entity classes mapped with JPA annotations (e.g., `Post`, `Account`)
- **Repository**: JPA Repositories for database operations
- **Service Layer**: Contains business logic (e.g., `PostService`, `AccountService`)
- **Controller Layer**: Handles HTTP requests and responses
- **View Layer**: HTML pages rendered using Thymeleaf
- **Security Configuration**: Role-based access for `ADMIN` and `EDITOR`

---

## 🚀 Features

### 🔐 Authentication & Authorization
- Login
- <img width="1897" height="908" alt="login" src="https://github.com/user-attachments/assets/ac06ca71-1735-4623-be56-af49a0459d3e" />

- Registration
- <img width="1896" height="910" alt="register form" src="https://github.com/user-attachments/assets/bcf03f1b-6e92-4ab4-b624-6a50eb79b0e4" />

- Spring Security-based role access:
  - `ADMIN`: Full access to all operations
  - `EDITOR`: Restricted to content creation/editing

### 📝 Blog Management (CRUD)
-  Add post
-  <img width="1896" height="911" alt="add post " src="https://github.com/user-attachments/assets/772636c3-de73-429e-b8e7-4efce9fd266e" />

-  Edit post
-  <img width="1886" height="903" alt="edit " src="https://github.com/user-attachments/assets/6cd43eea-11b5-4d65-b5ca-7b4a75a5a60c" />

-  Delete blog posts
-  <img width="1898" height="910" alt="Delete" src="https://github.com/user-attachments/assets/f444ea66-2f54-4a6f-9a9f-905ee361ee61" />

- View posts with author and timestamp
- <img width="1902" height="917" alt="01" src="https://github.com/user-attachments/assets/a78c3d79-15ee-4e8d-a903-12fe5aa119b1" />

- Pagination and sorting support
- <img width="1887" height="837" alt="pagination" src="https://github.com/user-attachments/assets/52c0326e-d6e6-4ebe-83c1-05aef268b790" />


### 📋 Admin & Editor Panel
- Role-based access to views and features
- Admin can manage all users and posts

---

## 🗄 Database (H2)

This project uses **H2 in-memory database**:
- No external setup required
- H2 Console available at: `http://localhost:8080/db-console`
- <img width="663" height="422" alt="db console" src="https://github.com/user-attachments/assets/35c9b179-8270-41ba-8e11-15d48e529f2b" />

- Configure JDBC URL, username (`admin`), and password in `application.properties`
- <img width="1917" height="620" alt="account" src="https://github.com/user-attachments/assets/3b06d9ee-f368-4662-b376-009cca3ca59d" />


```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:file:./db/blogdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
 
# H2 Console Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/db-console
spring.h2.console.settings.web-allow-others=false
 
# Hibernate DDL Auto Configuration
spring.jpa.hibernate.ddl-auto=create-drop 

🛠️ How to Run
Clone this repository

Open in IDE (IntelliJ/Eclipse)

Run the SpringStarterApplication.java class

Visit http://localhost:8080/home


📌 Technologies Used
🟦 Java 17 – Modern language features and performance for backend development

🚀 Spring Boot 3.5.3 – Rapid development with auto-configuration and embedded servers

🔐 Spring Security – Secure login, role-based access (ADMIN, EDITOR)

🗃️ Spring Data JPA – Simplified database access with repository abstractions

🖼️ Thymeleaf – Dynamic server-side HTML rendering with Spring MVC integration

🗄️ H2 Database – In-memory SQL database with a built-in web console for testing

⚙️ Maven – Dependency management and project build tool


👨‍💻 Author
Developed by Shivam Yadav
Feel free to contribute, raise issues, or suggest improvements.

