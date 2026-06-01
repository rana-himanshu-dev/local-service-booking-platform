# 🔧 Local Service Booking Platform

A full-stack web application that connects customers with local service providers (plumbers, electricians, tutors, salons). Built with Spring Boot, MySQL, and Bootstrap 5.

![Java](https://img.shields.io/badge/Java-23-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## 🌟 Features

### For Customers
- 🔍 Search providers by city, category, rating, and price
- 📅 Book available time slots instantly
- 💳 Secure payments via Razorpay
- ⭐ Rate and review service providers
- 📧 Email notifications for bookings

### For Service Providers
- 👤 Create and manage business profile
- ⏰ Manage availability with time slots
- 📊 View earnings and transaction history
- 📱 Receive booking notifications

### For Admins
- ✅ Approve/reject provider registrations
- 📈 Platform analytics dashboard
- 🎫 Support ticket management
- 👥 User management

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 4.0.6 |
| Language | Java 23 |
| Database | MySQL 8.0 |
| ORM | Hibernate 7.2 + Spring Data JPA |
| Security | Spring Security 7 + JWT |
| Payment | Razorpay |
| Frontend | Thymeleaf + Bootstrap 5 |
| Testing | JUnit 5 + Mockito |
| Build Tool | Maven |
| Version Control | Git + GitHub |

---

## 📋 Prerequisites

- Java 23+
- Maven 3.8+
- MySQL 8.0+
- IntelliJ IDEA (recommended)

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/rana-himanshu-dev/local-service-booking-platform
cd booking-platform
```

### 2. Create MySQL Database

```sql
CREATE DATABASE localservice_db;
```

### 3. Configure application.properties

Open `src/main/resources/application.properties` and update:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/localservice_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

app.jwt.secret=YOUR_JWT_SECRET
app.jwt.expiration=86400000

razorpay.key.id=YOUR_RAZORPAY_KEY_ID
razorpay.key.secret=YOUR_RAZORPAY_KEY_SECRET

spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_APP_PASSWORD
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

### 5. Access the Application