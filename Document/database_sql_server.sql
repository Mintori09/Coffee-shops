IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'CoffeeShop')
BEGIN
    CREATE DATABASE CoffeeShop;
END;
GO

USE CoffeeShop;
GO
IF OBJECT_ID('NhanVien', 'U') IS NOT NULL DROP TABLE NhanVien;
GO

CREATE TABLE NhanVien (
    MaNhanVien INT PRIMARY KEY,
    TenNhanVien NVARCHAR(255),
    SoDienThoai NVARCHAR(20),
    Email NVARCHAR(255),
    ChucVu NVARCHAR(100),
    ThoiGianTao DATETIME DEFAULT GETDATE(),
    ThoiGianCapNhat DATETIME DEFAULT GETDATE()
);
GO

INSERT INTO NhanVien VALUES
(1, N'Nguyễn Văn A', '0909123456', 'a@example.com', N'Thu ngân', '2025-05-12 13:52:11', '2025-05-12 13:52:11'),
(2, N'Trần Thị B', '0912345678', 'b@example.com', N'Pha chế', '2025-05-12 13:52:11', '2025-05-12 13:52:11');
GO
IF OBJECT_ID('HoaDon', 'U') IS NOT NULL DROP TABLE HoaDon;
GO

CREATE TABLE HoaDon (
    MaHoaDon INT PRIMARY KEY,
    MaNhanVien INT FOREIGN KEY REFERENCES NhanVien(MaNhanVien),
    ThoiGianThanhToan DATETIME,
    TongTienPhaiTra DECIMAL(10,2),
    TrangThai NVARCHAR(50),
    GhiChu NVARCHAR(MAX),
    ThoiGianTao DATETIME DEFAULT GETDATE()
);
GO

INSERT INTO HoaDon VALUES
(1, 1, '2025-05-12 13:52:11', 55000.00, N'Đã thanh toán', N'Khách thanh toán tiền mặt', '2025-05-12 13:52:11'),
(2, 2, '2025-05-12 13:52:11', 30000.00, N'Chưa thanh toán', N'', '2025-05-12 13:52:11');
GO
IF OBJECT_ID('LoaiMon', 'U') IS NOT NULL DROP TABLE LoaiMon;
GO

CREATE TABLE LoaiMon (
    MaLoaiMon INT PRIMARY KEY,
    TenLoaiMon NVARCHAR(255),
    MoTa NVARCHAR(MAX),
    PhanLoai NVARCHAR(MAX),
    HinhAnh NVARCHAR(100),
    ThoiGianTao DATETIME DEFAULT GETDATE(),
    ThoiGianCapNhat DATETIME DEFAULT GETDATE()
);
GO

INSERT INTO LoaiMon VALUES
(1, N'Cà phê sữa', N'Các loại cà phê truyền thống', N'Cà phê', 'images/milk_coffee.jpg', '2025-05-12 13:52:11', '2025-05-12 13:52:11'),
(2, N'Sinh tố', N'Sinh tố trái cây tươi', N'Sinh tố', 'images/sinh_to.jpg', '2025-05-12 13:52:11', '2025-05-12 13:52:11'),
(3, N'Trà sữa', N'Trà kết hợp với sữa và topping', N'Trà sữa', 'images/milk_tea.jpg', '2025-05-12 13:52:11', '2025-05-12 13:52:11');
GO
IF OBJECT_ID('ChiTietHoaDon', 'U') IS NOT NULL DROP TABLE ChiTietHoaDon;
GO

CREATE TABLE ChiTietHoaDon (
    MaChiTietHD INT PRIMARY KEY,
    MaHoaDon INT FOREIGN KEY REFERENCES HoaDon(MaHoaDon),
    MaMon INT, -- Chưa có bảng Món => để INT tạm thời
    SoLuong INT,
    DonGiaLucDat DECIMAL(10,2),
    ThanhTien DECIMAL(10,2),
    GhiChuMon NVARCHAR(255),
    ThoiGianTao DATETIME DEFAULT GETDATE()
);
GO

INSERT INTO ChiTietHoaDon (MaChiTietHD, MaHoaDon, MaMon, SoLuong, DonGiaLucDat, GhiChuMon, ThoiGianTao) VALUES
(1, 1, 1, 1, 20000.00, N'', '2025-05-12 13:52:11'),
(2, 1, 2, 1, 35000.00, N'Ít đá', '2025-05-12 13:52:11'),
(3, 2, 3, 1, 30000.00, N'Không đường', '2025-05-12 13:52:11');
GO
