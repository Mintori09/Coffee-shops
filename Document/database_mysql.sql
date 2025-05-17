DROP DATABASE IF EXISTS coffee;
CREATE DATABASE IF NOT EXISTS coffee;

USE coffee;

-- 1. Accounts
CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- 2. Employees
CREATE TABLE IF NOT EXISTS employees (
    id INT PRIMARY KEY,
    account_id INT,
    full_name NVARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(10),
    phone_number VARCHAR(20),
    role VARCHAR(20),
    hire_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

-- 3. Food categories
CREATE TABLE IF NOT EXISTS food_categories (
    food_category_id INT PRIMARY KEY,
    food_category_name NVARCHAR(100) NOT NULL
);

-- 4. Foods
CREATE TABLE IF NOT EXISTS foods (
    food_id INT PRIMARY KEY,
    food_name NVARCHAR(100) NOT NULL,
    food_category_id INT,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    description NVARCHAR(255),
    image NVARCHAR(255), -- Image path
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (food_category_id) REFERENCES food_categories(food_category_id)
);

-- 5. Bills
CREATE TABLE IF NOT EXISTS bills (
    bill_id INT PRIMARY KEY,
    order_date DATE NOT NULL DEFAULT CURRENT_DATE,
    total_price DECIMAL(10,2) DEFAULT 0,
    notes NVARCHAR(255),
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    employee_id INT, -- Foreign key to employees
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- 6. Bill details
CREATE TABLE IF NOT EXISTS bill_details (
    bill_id INT NOT NULL,
    food_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0), -- Price at time of sale
    notes NVARCHAR(255),
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (bill_id, food_id), -- Composite primary key
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id),
    FOREIGN KEY (food_id) REFERENCES foods(food_id)
);

-- 7. Trigger to update total_price in bills
DELIMITER //

CREATE TRIGGER tg_update_total_price_after_insert
AFTER INSERT ON bill_details
FOR EACH ROW
BEGIN
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_id = NEW.bill_id
    )
    WHERE bill_id = NEW.bill_id;
END;
//

DELIMITER ;
DELIMITER //

CREATE TRIGGER tg_update_total_price_after_update
AFTER UPDATE ON bill_details
FOR EACH ROW
BEGIN
    -- Cập nhật bill cũ nếu bill_id bị đổi
    IF OLD.bill_id != NEW.bill_id THEN
        UPDATE bills
        SET total_price = (
            SELECT SUM(quantity * price)
            FROM bill_details
            WHERE bill_id = OLD.bill_id
        )
        WHERE bill_id = OLD.bill_id;
    END IF;

    -- Cập nhật bill mới
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_id = NEW.bill_id
    )
    WHERE bill_id = NEW.bill_id;
END;
//

DELIMITER ;
DELIMITER //

CREATE TRIGGER tg_update_total_price_after_delete
AFTER DELETE ON bill_details
FOR EACH ROW
BEGIN
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_id = OLD.bill_id
    )
    WHERE bill_id = OLD.bill_id;
END;
//

DELIMITER ;
