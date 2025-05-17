-- Xóa và tạo lại cơ sở dữ liệu
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
    id INT IDENTITY(1,1) PRIMARY KEY,
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

-- 3. Drink categories (renamed from food_categories)
CREATE TABLE drink_categories (
    food_category_id INT IDENTITY(1,1) PRIMARY KEY,
    food_category_name NVARCHAR(100) NOT NULL
);
GO

-- 4. Drinks (renamed from foods)
CREATE TABLE drinks (
    food_id INT IDENTITY(1,1) PRIMARY KEY,
    food_name NVARCHAR(100) NOT NULL,
    food_category_id INT,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    description NVARCHAR(255),
    image NVARCHAR(255),
    created_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (food_category_id) REFERENCES drink_categories(food_category_id)
);
GO

-- 5. Bills
CREATE TABLE bills (
    bill_id INT IDENTITY(1,1) PRIMARY KEY,
    order_date DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    total_price DECIMAL(10, 2) DEFAULT 0,
    notes NVARCHAR(255),
    created_date DATETIME DEFAULT GETDATE(),
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);
GO

-- 6. Bill Details
CREATE TABLE bill_details (
    bill_id INT NOT NULL,
    food_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    notes NVARCHAR(255),
    created_date DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (bill_id, food_id),
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id),
    FOREIGN KEY (food_id) REFERENCES drinks(food_id)
);
GO

-- 7. Trigger: AFTER INSERT
CREATE TRIGGER trg_update_total_price_after_insert
ON bill_details
AFTER INSERT
AS
BEGIN
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_details.bill_id = bills.bill_id
    )
    WHERE bill_id IN (SELECT DISTINCT bill_id FROM inserted);
END;
GO

-- 8. Trigger: AFTER UPDATE
CREATE TRIGGER trg_update_total_price_after_update
ON bill_details
AFTER UPDATE
AS
BEGIN
    -- Cập nhật hóa đơn cũ nếu bill_id thay đổi
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_details.bill_id = bills.bill_id
    )
    WHERE bill_id IN (
        SELECT DISTINCT bill_id FROM deleted
        WHERE bill_id NOT IN (SELECT bill_id FROM inserted)
    );

    -- Cập nhật hóa đơn mới
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_details.bill_id = bills.bill_id
    )
    WHERE bill_id IN (SELECT DISTINCT bill_id FROM inserted);
END;
GO

-- 9. Trigger: AFTER DELETE
CREATE TRIGGER trg_update_total_price_after_delete
ON bill_details
AFTER DELETE
AS
BEGIN
    UPDATE bills
    SET total_price = (
        SELECT SUM(quantity * price)
        FROM bill_details
        WHERE bill_details.bill_id = bills.bill_id
    )
    WHERE bill_id IN (SELECT DISTINCT bill_id FROM deleted);
END;
GO
