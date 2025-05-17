-- Xóa và tạo lại database
IF DB_ID('coffee') IS NOT NULL
    DROP DATABASE coffee;
GO

CREATE DATABASE coffee;
GO

USE coffee;
GO

-- 1. Accounts
CREATE TABLE accounts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
GO

-- 2. Employees
CREATE TABLE employees (
    id INT PRIMARY KEY,
    account_id INT,
    full_name NVARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(10),
    phone_number VARCHAR(20),
    role VARCHAR(20),
    hire_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);
GO

-- 3. Food categories
CREATE TABLE food_categories (
    food_category_id INT PRIMARY KEY,
    food_category_name NVARCHAR(100) NOT NULL
);
GO

-- 4. Foods
CREATE TABLE foods (
    food_id INT PRIMARY KEY,
    food_name NVARCHAR(100) NOT NULL,
    food_category_id INT,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    description NVARCHAR(255),
    image NVARCHAR(255), -- Image path
    created_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (food_category_id) REFERENCES food_categories(food_category_id)
);
GO

-- 5. Bills
CREATE TABLE bills (
    bill_id INT PRIMARY KEY,
    order_date DATE NOT NULL DEFAULT GETDATE(),
    total_price DECIMAL(10,2) DEFAULT 0,
    notes NVARCHAR(255),
    created_date DATETIME DEFAULT GETDATE(),
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);
GO

-- 6. Bill details
CREATE TABLE bill_details (
    bill_id INT NOT NULL,
    food_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    notes NVARCHAR(255),
    created_date DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (bill_id, food_id),
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id),
    FOREIGN KEY (food_id) REFERENCES foods(food_id)
);
GO
-- Trigger AFTER INSERT
CREATE TRIGGER tg_update_total_price_after_insert
ON bill_details
AFTER INSERT
AS
BEGIN
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_id = inserted.bill_id
    )
    FROM bills
    JOIN inserted ON bills.bill_id = inserted.bill_id;
END;
GO

-- Trigger AFTER UPDATE
CREATE TRIGGER tg_update_total_price_after_update
ON bill_details
AFTER UPDATE
AS
BEGIN
    -- Cập nhật hóa đơn cũ (nếu bill_id thay đổi)
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_id = deleted.bill_id
    )
    FROM bills
    JOIN deleted ON bills.bill_id = deleted.bill_id;

    -- Cập nhật hóa đơn mới
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_id = inserted.bill_id
    )
    FROM bills
    JOIN inserted ON bills.bill_id = inserted.bill_id;
END;
GO

-- Trigger AFTER DELETE
CREATE TRIGGER tg_update_total_price_after_delete
ON bill_details
AFTER DELETE
AS
BEGIN
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_id = deleted.bill_id
    )
    FROM bills
    JOIN deleted ON bills.bill_id = deleted.bill_id;
END;
GO
