-- Create employees table
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email_id VARCHAR(255) NOT NULL UNIQUE
);

-- Create attendance table
CREATE TABLE IF NOT EXISTS attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    check_in_time DATETIME,
    check_out_time DATETIME,
    date DATE NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    UNIQUE KEY unique_employee_date (employee_id, date)
);

-- Insert sample employees
INSERT IGNORE INTO employees (first_name, last_name, email_id) VALUES
('John', 'Doe', 'john.doe@company.com'),
('Jane', 'Smith', 'jane.smith@company.com'),
('Mike', 'Johnson', 'mike.johnson@company.com'),
('Sarah', 'Wilson', 'sarah.wilson@company.com');