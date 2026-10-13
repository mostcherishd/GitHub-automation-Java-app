# EMS Backend

Employee Management System Backend API built with Spring Boot.

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

## Step-by-Step Setup

### 1. Database Setup
```sql
mysql -u root -p
CREATE DATABASE employee_db;
EXIT;
```

### 2. Configure Database (Optional)
Edit `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=root
spring.datasource.password=password
```

### 3. Navigate to Backend Directory
```bash
cd ems-backend
```

### 4. Run Application
```bash
mvn spring-boot:run
```

### 5. Verify Setup
Backend runs on: http://localhost:8082
Test API: http://localhost:8082/api/v1/employees

## Features
- Employee CRUD operations
- Attendance tracking (check-in/check-out)
- Real-time attendance dashboard data
- MySQL database integration
- Sample data auto-loaded

## API Endpoints

### Employee Management
- `GET /api/v1/employees` - Get all employees
- `POST /api/v1/employees` - Create new employee
- `GET /api/v1/employees/{id}` - Get employee by ID
- `PUT /api/v1/employees/{id}` - Update employee
- `DELETE /api/v1/employees/{id}` - Delete employee

### Attendance Management
- `POST /api/v1/attendance/checkin` - Employee check-in
- `POST /api/v1/attendance/checkout` - Employee check-out
- `GET /api/v1/attendance/today` - Get today's attendance

## Sample Data
Automatically creates sample employees:
- John Doe (john.doe@company.com)
- Jane Smith (jane.smith@company.com)
- Mike Johnson (mike.johnson@company.com)
- Sarah Wilson (sarah.wilson@company.com)

## Troubleshooting
- **Port 8082 busy:** `lsof -ti:8082 | xargs kill -9`
- **Database connection failed:** Check MySQL is running and database exists
- **Build errors:** Run `mvn clean install`