# Online Pet Adoption Platform

A web-based pet adoption system built as a college project using **Java Servlets**, **JDBC**, **MariaDB**, and **Apache Tomcat**.

The platform connects **Adopters**, **Shelters**, and an **Admin** to manage pets, adoption applications, and users.

---

## 👥 Team & Responsibilities

| Members          | Role                          | Features                                              |
|------------------|-------------------------------|-------------------------------------------------------|
| Arth Bania       | Admin + Core Auth             | Login/Register, Admin Dashboard, Manage Users & Pets  |
| Vanshika Arora   | Shelter Features              | Add Pet, View My Pets, Manage Applications            |
| Divyansh Rajpoot | Adopter Features              | Browse Pets, Apply for Adoption, View My Applications |

**GitHub Repository:** https://github.com/Arthbania14/PetAdoptionPlatform

---

## 🛠️ Tech Stack

- **Backend:** Java Servlets + JDBC
- **Database:** MariaDB
- **Server:** Apache Tomcat (via SmartTomcat plugin in IntelliJ IDEA)
- **Build Tool:** Maven
- **Frontend:** JSP + HTML + CSS + Bootstrap (recommended)
- **IDE:** IntelliJ IDEA

---

## ✨ Features

### Common
- User Registration & Login
- Role-based redirection after login
- Session management
- Logout

### Admin
- Admin Dashboard
- View all Users
- View all Pets
- Update Pet Status

### Shelter
- Shelter Dashboard
- Add new Pet
- View / Manage own Pets
- Manage Adoption Applications for own pets

### Adopter
- Adopter Dashboard
- Browse available Pets
- View Pet Details
- Apply for Adoption
- View My Applications (status tracking)

---

## 📁 Project Structure

```
PetAdoptionPlatform/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/petadoption/          (or your package name)
│       │       ├── model/                # POJOs (User, Pet, AdoptionApplication, etc.)
│       │       ├── dao/                  # Database access (UserDAO, PetDAO, etc.)
│       │       ├── controller/           # Servlets
│       │       └── util/                 # DBConnection, helpers
│       ├── resources/
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml
│           ├── css/
│           ├── js/
│           ├── images/
│           ├── admin/                    # Admin JSPs
│           ├── shelter/                  # Shelter JSPs
│           ├── adopter/                  # Adopter JSPs
│           ├── login.jsp
│           ├── register.jsp
│           └── index.jsp
├── pom.xml
└── README.md
```

---

## 🗄️ Database

**Database Name:** `pet_adoption`

### Main Tables
- `users` – All users (Admin, Shelter, Adopter)
- `shelters` – Shelter-specific details
- `pets` – Pet listings
- `adoption_applications` – Adoption requests
- `messages` – Messaging between users (optional/future)

---

## ⚙️ Prerequisites

1. **JDK 17** or higher
2. **MariaDB** / MySQL
3. **IntelliJ IDEA** (Community or Ultimate)
4. **SmartTomcat** plugin (for running the project easily)
5. **Maven** (usually bundled with IntelliJ)

---

## 🚀 How to Run the Project

### 1. Clone the Repository
```bash
git clone https://github.com/Arthbania14/PetAdoptionPlatform.git
cd PetAdoptionPlatform
```

### 2. Create the Database
1. Open MariaDB / MySQL
2. Create database:
   ```sql
   CREATE DATABASE pet_adoption;
   ```
3. Import the SQL script (if provided by the team) or run the table creation statements.

### 3. Configure Database Connection
Open `DBConnection.java` (in `util` package) and update:

```java
private static final String URL = "jdbc:mariadb://localhost:3306/pet_adoption";
private static final String USER = "root";          // your username
private static final String PASSWORD = "yourpassword"; // your password
```

### 4. Open in IntelliJ IDEA
1. Open the project as a **Maven** project
2. Wait for Maven dependencies to download
3. Make sure the SmartTomcat plugin is installed

### 5. Run with SmartTomcat
1. Go to **Run → Edit Configurations**
2. Add **Smart Tomcat** configuration
3. Set:
   - **Tomcat Server** → your local Tomcat
   - **Deployment** → the `webapp` folder or the exploded WAR
4. Click **Run**


---

## 📌 Default Roles

After registration or seed data, users are redirected based on role:

| Role     | Redirect Page              |
|----------|----------------------------|
| ADMIN    | Admin Dashboard            |
| SHELTER  | Shelter Dashboard          |
| ADOPTER  | Adopter Dashboard          |

---
