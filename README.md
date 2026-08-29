# 🔐 Spring Security + JWT Authentication

A beginner-friendly **Spring Boot project demonstrating Spring Security with JWT (JSON Web Token) authentication and authorization**.

This project is designed especially for **fresher Java/Spring Boot developers** who want to understand how authentication and authorization work in a real-world Spring Boot application.

---

## 📌 About This Project

This project demonstrates how to implement:

* 🔐 Spring Security
* 🎟️ JWT Authentication
* 👤 User Registration
* 🔑 User Login
* 🔒 Password Encryption using BCrypt
* 🛡️ Role-Based Authorization
* 🔑 JWT Token Generation
* ✅ JWT Token Validation
* 🚫 Securing REST APIs
* 🔄 Stateless Authentication
* 🧩 Custom `UserDetailsService`
* ⚙️ Security Filter Chain
* 🔍 JWT Authentication Filter

The main purpose of this project is to provide a **simple and easy-to-understand implementation of Spring Security with JWT**.

---

## 🛠️ Technologies Used

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java            | Programming Language           |
| Spring Boot     | Backend Framework              |
| Spring Security | Authentication & Authorization |
| JWT             | Token-Based Authentication     |
| Spring Data JPA | Database Operations            |
| MySQL           | Database                       |
| Maven           | Dependency Management          |
| Lombok          | Reduce Boilerplate Code        |
| REST API        | Client-Server Communication    |

---



# 🔐 Authentication Flow

The basic authentication flow of this project is:

```text
              ┌──────────────┐
              │    Client    │
              └──────┬───────┘
                     │
                     │ Register
                     ▼
              ┌──────────────┐
              │   Register   │
              │     API      │
              └──────┬───────┘
                     │
                     ▼
              Password encrypted
                 using BCrypt
                     │
                     ▼
                ┌─────────┐
                │ Database│
                └─────────┘


              Login Request
                     │
                     ▼
              ┌──────────────┐
              │ Login API    │
              └──────┬───────┘
                     │
              Username/Password
                     │
                     ▼
              Authentication
                     │
                     ▼
              ┌──────────────┐
              │ JWT Service  │
              └──────┬───────┘
                     │
                     ▼
                JWT Token
                     │
                     ▼
                  Client
                     │
              Authorization:
              Bearer <token>
                     │
                     ▼
          ┌─────────────────────┐
          │ JWT Authentication  │
          │       Filter        │
          └──────────┬──────────┘
                     │
              Validate JWT
                     │
                     ▼
             Security Context
                     │
                     ▼
              Protected API
```

---

# 🚀 How JWT Authentication Works

### 1. User Registration

The user sends registration information:

```json
{
  "username": "sagar",
  "password": "123456"
}
```

The password should **never be stored as plain text**.

Spring Security's `BCryptPasswordEncoder` is used to encrypt the password.

```text
123456
   ↓
BCryptPasswordEncoder
   ↓
$2a$10$..............
```

The encrypted password is stored in the database.

---

### 2. User Login

The user sends their username and password:

```http
POST /api/auth/login
```

Example:

```json
{
  "username": "sagar",
  "password": "123456"
}
```

Spring Security verifies the credentials.

If the credentials are correct, the application generates a JWT.

Example:

```text
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWdhciJ9.xxxxxxxxx
```

---

### 3. Client Stores the JWT

The client receives the token.

For example:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

The client then sends this token whenever it accesses a protected API.

---

### 4. Send JWT With Request

The token is normally sent in the HTTP `Authorization` header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Example:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

### 5. JWT Filter

The custom JWT filter intercepts incoming requests.

It:

1. Reads the `Authorization` header.
2. Extracts the JWT.
3. Validates the JWT.
4. Gets the username from the token.
5. Loads the user from the database.
6. Creates an authenticated `SecurityContext`.
7. Allows the request to continue.

If the token is invalid or expired, the request is rejected.

---

# 🔒 Password Encryption

Passwords should never be stored directly in the database.

Use:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

When registering:

```java
String encodedPassword =
        passwordEncoder.encode(user.getPassword());
```

Store the encoded password instead of the original password.

---

# ⚙️ Security Configuration

Spring Security uses a `SecurityFilterChain` to configure application security.

A typical configuration looks like:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(session ->
            session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
            )
        );

    return http.build();
}
```

### Important Points

`csrf().disable()`

Used when building a stateless REST API where authentication is handled through JWT rather than a traditional server-side session.

`permitAll()`

Allows unauthenticated access to endpoints such as registration and login.

`authenticated()`

Requires authentication for protected endpoints.

`STATELESS`

Tells Spring Security not to create or maintain an HTTP session for authentication.

---

# 🎟️ JWT Authentication Filter

The JWT filter runs before Spring Security's normal authentication processing.

Typical flow:

```text
HTTP Request
     ↓
JWT Filter
     ↓
Extract Token
     ↓
Validate Token
     ↓
Load User
     ↓
Set Authentication
     ↓
SecurityContext
     ↓
Controller
```

The filter commonly extends:

```java
OncePerRequestFilter
```

Example:

```java
@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Extract JWT
        // Validate JWT
        // Authenticate user

        filterChain.doFilter(request, response);
    }
}
```

---

# 🛡️ Role-Based Authorization

Spring Security can also restrict APIs based on user roles.

For example:

```text
USER
ADMIN
```

Example:

```java
.requestMatchers("/api/admin/**")
.hasRole("ADMIN")
```

Only users with the `ADMIN` role can access:

```text
/api/admin/**
```

A normal user will not be allowed to access the endpoint.

---

# 🌐 Example APIs

## Register

```http
POST /api/auth/register
```

Request:

```json
{
  "username": "sagar",
  "password": "123456"
}
```

---

## Login

```http
POST /api/auth/login
```

Request:

```json
{
  "username": "sagar",
  "password": "123456"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## Public API

```http
GET /api/public
```

This endpoint does not require authentication.

---

## Protected API

```http
GET /api/user/profile
```

Request header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Without a valid JWT, access will be denied.

---

# 🗄️ Database Configuration

Configure your MySQL database in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/security_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

⚠️ **Do not upload your real database password or JWT secret to GitHub.**

Use environment variables or an external configuration file for sensitive values.

---

# 📦 Maven Dependencies

The project requires dependencies for:

* Spring Web
* Spring Security
* Spring Data JPA
* MySQL Driver
* JWT
* Lombok
* Validation

Example Spring Security dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

For JWT, use a JWT library such as JJWT according to the version configured in this project.

---

# ▶️ How to Run the Project

### Step 1 — Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

### Step 2 — Open the Project

Open the project in:

* IntelliJ IDEA
* Eclipse / Spring Tool Suite
* VS Code

### Step 3 — Configure MySQL

Create the database:

```sql
CREATE DATABASE security_db;
```

Update your database credentials in:

```text
application.properties
```

### Step 4 — Build the Project

```bash
mvn clean install
```

### Step 5 — Run the Application

```bash
mvn spring-boot:run
```

Or run the main Spring Boot application class directly.

---

# 🧪 Testing the APIs

You can test the APIs using:

* Postman
* Insomnia
* Thunder Client
* Frontend application

### Recommended Testing Order

```text
1. Register User
       ↓
2. Login User
       ↓
3. Copy JWT Token
       ↓
4. Open Protected API
       ↓
5. Add Authorization Header
       ↓
6. Send Request
```

Header:

```http
Authorization: Bearer <YOUR_TOKEN>
```

---

# 🧠 Important Concepts for Freshers

If you are learning Spring Security and JWT, understand these concepts:

### Spring Security

Framework used to provide authentication and authorization in Spring applications.

### Authentication

Answers:

> "Who are you?"

Example:

```text
Username + Password
```

### Authorization

Answers:

> "What are you allowed to access?"

Example:

```text
ADMIN → Admin APIs
USER  → User APIs
```

### JWT

A JSON Web Token is a compact token used to securely transmit authentication-related information between parties.

### BCrypt

Used to securely hash passwords before storing them in the database.

### SecurityFilterChain

Defines how Spring Security handles incoming HTTP requests.

### OncePerRequestFilter

Useful for implementing a custom JWT authentication filter.

### SecurityContext

Stores the authentication information for the current request.

---

# 🔄 Complete Request Flow

```text
Client
  │
  │ Login
  ▼
Authentication API
  │
  │ Username + Password
  ▼
AuthenticationManager
  │
  ▼
UserDetailsService
  │
  ▼
Database
  │
  ▼
Password Verification
  │
  ▼
JWT Generation
  │
  ▼
Client receives JWT
  │
  │
  │ Authorization: Bearer JWT
  ▼
Protected API
  │
  ▼
JWT Authentication Filter
  │
  ▼
JWT Validation
  │
  ▼
SecurityContext
  │
  ▼
Controller
  │
  ▼
Response
```

---

# 🔐 Security Best Practices

When using this project in a real application:

* Never store plain-text passwords.
* Always hash passwords using a secure password encoder.
* Never commit JWT secrets to GitHub.
* Never commit database passwords.
* Use HTTPS in production.
* Keep JWT expiration times reasonable.
* Validate JWT signatures and expiration.
* Use appropriate roles and permissions.
* Keep dependencies updated.
* Store secrets using environment variables or a secret-management system.

---

# 🎯 Learning Objective

After completing this project, a beginner should understand:

```text
Spring Security
      +
Authentication
      +
Authorization
      +
BCrypt Password Encoding
      +
JWT Generation
      +
JWT Validation
      +
Security Filter
      +
Role-Based Authorization
```

This project can be used as a **starting point for implementing JWT-based authentication in Spring Boot REST APIs**.

---
This is your application.propertites file 
# ================================
# 🗄️ MySQL Database Configuration
# ================================

spring.datasource.url=jdbc:mysql://localhost:3306/sdb
spring.datasource.username=root
spring.datasource.password=YOUR_DATABASE_PASSWORD

spring.jpa.hibernate.ddl-auto=update


# ================================
# 🔐 JWT Configuration
# ================================

jwt.secret=YOUR_JWT_SECRET
jwt.issuer=SpringDemoSecurity
jwt.expiry=900

# 👨‍💻 Author

**Sagar Vijay Kokare**

Java | Spring Boot | Spring Security | JWT | REST API

---

## ⭐ If This Project Helped You

If you find this project useful for learning **Spring Security + JWT**, consider giving the repository a ⭐ on GitHub.

Happy Coding! 🚀
