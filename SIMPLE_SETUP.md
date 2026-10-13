# Employee Management System - Simple Setup

## Prerequisites
- Java 17+
- Node.js 16+
- MySQL 8.0+
- Maven 3.6+

## Step 1: Database Setup
```sql
mysql -u root -p

CREATE DATABASE employee_db;
EXIT;
```

## Step 2: Start Backend
```bash
cd ems-backend
mvn spring-boot:run
```
Backend runs on: http://localhost:8082

## Step 3: Start Frontend
```bash
cd ems-frontend
npm install
npm start
```
Frontend runs on: http://localhost:3000

## Step 4: Access Application
Open browser: http://localhost:3000

## Default Data
Sample employees are automatically created:
- John Doe
- Jane Smith  
- Mike Johnson
- Sarah Wilson

## Troubleshooting
- **Port 8082 busy:** `lsof -ti:8082 | xargs kill -9`
- **Port 3000 busy:** `lsof -ti:3000 | xargs kill -9`
- **Database error:** Check MySQL is running and database exists