#  Blog Platform API

## Project Description 
This project aims to develop a backend system for a blog platform. 

The API provides a way for users to register, login and read/publish blog posts and comments.

It is developed with Spring boot and uses JWT for authentification.

## Currently Available Main Features

### For Anyone
* **Registration/Sign up** 
* **Login/Sign in**

### For User
* **Draft Post**
* **View a list of their own Draft Post**
* **Publish/Update/Delete Post**
* **Read All Posts**  
* **Post Comments**

### For Admin
* **View a List of Users**
* **Register User As Admin** (Give users Admin role)  


## Technology Stack 
The project is built using the following technologies and frameworks:

* **Java 17**
* **Spring Boot** with Spring Security (JWT-based authentication)
* **MySQL** – relational database
* **Maven** – build tool
* **Docker & Docker Compose** – containerization
* **JUnit 5 & Mockito** – automated testing


## How To Run the Project 

### Run Locally (without Docker)

**Prerequisites:**

* Java 17 or later
* Maven
* MySQL

1. Create a local MySQL database:

```sql
CREATE DATABASE blog_platform;
```

2. Set environment variables (example):
The application uses the default values defined in application.properties when no environment variables are provided.

Default local configuration:

* Database URL: jdbc:mysql://localhost:3306/blog_db
* Username: root
* Password: password

You may override these values using environment variables:

| Variable                     | Description       |
|------------------------------|-------------------|
| `SPRING_DATASOURCE_URL`      | Database URL      |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |

3. Start the application:

```bash
mvn spring-boot:run
```

The application will be available at:

```
http://localhost:8080
```

---

### Run with Docker

**Prerequisites:**

* Docker
* Docker Compose

1. Create a `.env` file (not committed to Git) based on `env.example`:


```env
MYSQL_DATABASE=blog_db
MYSQL_USER_ROOT=root
MYSQL_ROOT_PASSWORD=password
PORT_TO_EXPOSE=8080
```

2. Build and start the containers:

```bash
docker-compose up --build
```

3. The API is exposed on the host machine at the port defined by `PORT_TO_EXPOSE`.

```
http://localhost:8080
```

---
## API Examples

### Authentication

#### Register User 

**POST** `/api/auth/users`

```json
{
  "username": "user",
  "password": "password123"
}
```

#### Login

**POST** `/api/auth/login`

```json
{
  "username": "user",
  "password": "password123"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Blog Posts (JWT Authentification required)

#### Get all posts

**GET** `/api/posts`

#### Create new post 

**POST** `/api/posts`

```json
{
  "title": "My first blog post",
  "content": "This is the content of the post",
  "categoryId":1,
  "status": "PUBLISHED"
}
```

#### Update post

**PUT** `/api/posts/{id}`

```json
{
  "title": "Updated title",
  "content": "Updated content",
  "categoryId":1,
  "status": "PUBLISHED"
}
```

#### Delete post

**DELETE** `/api/posts/{id}`


#### Create a draft 

**POST** `/api/posts`

```json
{
  "title": "My first blog post",
  "content": "This is the content of the post",
  "categoryId":1,
  "status": "DRAFT"
}
```

#### Get all draft that logged in user created 

**GET** `/api/posts/drafts`

#### Publish a Draft

**PUT** `/api/posts/drafts/{id}`

---

### Comments  (JWT Authentification required)

#### Add comment to post

**POST** `/api/comments`

```json
{
  "postId":1,
  "content": "Great post!"
}
```
___

### Category  (JWT Authentification required)

#### Add category

**POST** `/api/categories

```json
{
  "categoryName":"Art"
}
```
#### Delete category

**DELETE** `/api/categories/{id}`

___

### ONLY FOR ADMIN  
(JWT Authentification "ADMIN" is required. Needs to be registered as "ADMIN" beforehand.)

#### View All User information 

**GET** `/api/admin`

#### Register User as Admin

**POST** `/api/auth/users`

```json
{
  "username": "user"
}
```

---

## Testing

The project includes automated unit and integration tests for service and controller layers. Tests can be executed with:

```bash
mvn test
```

---

## Known Limitations

* No pagination or sorting for listing posts
* Simplified role and authorization handling
* No frontend – backend/API only
* No JWT refresh tokens (access token only)




