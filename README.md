# 🖥️ TourEase – Backend API (Java Servlets + JDBC + MySQL)

## 📘 Overview

This repository contains the **backend implementation** of **TourEase**, a role-based tour management web application developed as part of a Final Year Project (FYP). The backend is built using **Java Servlets**, **JDBC**, and **MySQL**, supporting RESTful APIs for role-based functionalities for **Tourists**, **Tour Companies**, and **Admins**.

---

## ⚙️ Technologies Used

| Layer           | Technology          |
|------------------|---------------------|
| Backend Language | Java (Servlets)     |
| Database         | MySQL               |
| Data Access      | JDBC                |
| API Type         | RESTful Endpoints   |
| Deployment       | Apache Tomcat       |
| Tools            | MySQL Workbench     |

---

## 🔐 Role-Based Features

### 🧳 Tourist
- View & search tours
- Book and cancel tours
- View personal bookings
- Submit reviews
- Update profile

### 🏢 Tour Company
- Create, update, and manage tour packages
- View bookings and customer details
- Manage company profile

### 👨‍💼 Admin
- Approve/reject companies
- View all users and bookings
- Admin dashboard to monitor activity

---

## 🗃️ Database Schema

- Users (Tourists, Companies, Admin)
- Tours
- Bookings
- Reviews
- Favorites

> All tables are related using foreign keys and follow normalization best practices.

---

---

## 🚀 How to Run the Project Locally

1. **Clone the repository**  
   ```bash
   git clone https://github.com/mhassanobaid/FypProjBE.git
   cd FypProjBE
2. **Import into your Java IDE (e.g., Eclipse or IntelliJ IDEA)**

3. **Set up your MySQL database**

4. **Create a schema tourease_db**

5- **Update DB credentials in your DBConnection.java**

6- **Deploy to Tomcat server**

7- **Build project**
🌐 Frontend Repository
The frontend is developed using React.js and can be found here:
➡️ TourEase Frontend Repo
👉 [Figma Design – TourEase](https://www.figma.com/design/LriVHSAOcTuUmjUlS0OBiv/Adventure-Ace?node-id=0-1&p=f)

🎓 Developed By
Muhammad Hassan Obaid

Muhammad Hamza Riaz

Muhammad Zubair Shabbir

Muhammad Usman Sarwar
