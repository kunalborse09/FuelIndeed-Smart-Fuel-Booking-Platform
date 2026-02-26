# ⛽ FuelIndeed – Smart Fuel Booking Platform

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-purple)
![License](https://img.shields.io/badge/Status-Active-success)

A full-stack web application that enables users to book fuel online from nearby fuel stations with secure authentication, admin approval workflow, and delivery management system.

FuelIndeed modernizes traditional fuel purchasing by providing a structured, role-based digital platform.

---

## 🚀 Key Features

### 👤 User Module
- Secure Registration & Login
- View Approved Fuel Stations
- Book Petrol / Diesel
- Track Booking Status
- View Booking History
- Profile Management

### ⛽ Fuel Station Module
- Register Fuel Station
- Admin Approval System
- Manage Fuel Availability
- View and Process Bookings

### 🚚 Delivery Module
- Delivery Person Registration
- Station Assignment
- View Assigned Orders
- Update Delivery Status

### 🛠 Admin Module
- Approve / Reject Fuel Stations
- Manage Users
- Monitor All Bookings
- Dashboard Analytics

---

## 🏗️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA (Hibernate)

### Frontend
- HTML5
- CSS3
- Bootstrap 5
- Thymeleaf

### Database
- MySQL

### Tools
- Eclipse IDE
- Maven
- Git & GitHub
- XAMPP / MySQL Workbench

---

## 🔐 Authentication & Authorization

Role-Based Access Control implemented using Spring Security.

Roles:
- ROLE_ADMIN
- ROLE_USER
- ROLE_STATION
- ROLE_DELIVERY

Each role has protected dashboards and restricted access endpoints.

---

## 🗂 Project Structure

```
FuelIndeed
│── controller
│── service
│── repository
│── entity
│── enums
│── config
│── templates
│── static
```

Architecture Pattern:
- Controller Layer
- Service Layer
- Repository Layer
- Entity Layer

---

## 🖼️ Project Screenshots

### 🏠 Home Page
![Home Page](images/home.png)

### 👤 User Dashboard
![User Dashboard](images/user-dashboard.png)

### 🛠 Admin Dashboard
![Admin Dashboard](images/admin-dashboard.png)

### ⛽ Fuel Station Panel
![Station Panel](images/station-panel.png)

### 🚚 Delivery Dashboard
![Delivery Dashboard](images/delivery-dashboard.png)

---

## ⚙️ Installation & Setup Guide

### 1️⃣ Clone Repository

```bash
git clone https://github.com/kunalborse09/FuelIndeed-Smart-Fuel-Booking-Platform.git
```

### 2️⃣ Open in Eclipse

- Import → Existing Maven Project
- Select the cloned folder

### 3️⃣ Configure MySQL Database

Create database:

```sql
CREATE DATABASE fuelindeed;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fuelindeed
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 4️⃣ Run Application

Run:

```
FuelIndeedApplication.java
```

Application URL:

```
http://localhost:8080
```

---

## 📈 Future Enhancements

- Online Payment Gateway Integration
- Live Delivery Tracking (Google Maps API)
- SMS / Email Notifications
- Fuel Price Auto Updates
- REST API Version
- Microservices Architecture Upgrade

---

## 🎯 Learning Outcomes

- Full Stack Development with Spring Boot
- Role-Based Authentication
- Database Relationship Mapping
- Secure Web Application Development
- MVC Architecture Implementation
- Git Version Control

---

## 👨‍💻 Developed By

**Kunal Borase**  
B.Tech Graduate  
Java Full Stack Developer  

GitHub: https://github.com/kunalborse09

---

## 📄 License

This project is developed for educational and demonstration purposes.
