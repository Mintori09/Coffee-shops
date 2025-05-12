
IF DB_ID('CoffeeShop') IS NULL
    CREATE DATABASE CoffeeShop;
GO

USE CoffeeShop;
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

INSERT INTO NhanVien (MaNhanVien, TenNhanVien, SoDienThoai, Email, ChucVu, ThoiGianTao, ThoiGianCapNhat) VALUES
(1, N'Nguyễn Văn A', N'0909123456', N'a@example.com', N'Thu ngân', GETDATE(), GETDATE()),
(2, N'Trần Thị B', N'0912345678', N'b@example.com', N'Pha chế', GETDATE(), GETDATE());
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

INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu, ThoiGianTao) VALUES
(1, 1, '2025-05-12 13:52:11', 55000.00, N'Đã thanh toán', N'Khách thanh toán tiền mặt', GETDATE()),
(2, 2, '2025-05-12 13:52:11', 30000.00, N'Chưa thanh toán', NULL, GETDATE());
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

INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu, ThoiGianTao) VALUES
(1, 1, '2025-05-12 13:52:11', 55000.00, N'Đã thanh toán', N'Khách thanh toán tiền mặt', GETDATE()),
(2, 2, '2025-05-12 13:52:11', 30000.00, N'Chưa thanh toán', NULL, GETDATE());
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

INSERT INTO LoaiMon (MaLoaiMon, TenLoaiMon, MoTa, PhanLoai, HinhAnh, ThoiGianTao, ThoiGianCapNhat) VALUES
(1, N'Cà phê sữa', N'Các loại cà phê truyền thống', N'Cà phê', N'images/milk_coffee.jpg', GETDATE(), GETDATE()),
(2, N'Sinh tố', N'Sinh tố trái cây tươi', N'Sinh tố', N'images/sinh_to.jpg', GETDATE(), GETDATE()),
(3, N'Trà sữa', N'Trà kết hợp với sữa và topping', N'Trà sữa', N'images/milk_tea.jpg', GETDATE(), GETDATE());
GO

CREATE TABLE ChiTietHoaDon (
    MaChiTietHD INT PRIMARY KEY,
    MaHoaDon INT FOREIGN KEY REFERENCES HoaDon(MaHoaDon),
    MaMon INT,
    SoLuong INT,
    DonGiaLucDat DECIMAL(10,2),
    GhiChuMon NVARCHAR(255),
    ThoiGianTao DATETIME DEFAULT GETDATE()
);
GO

INSERT INTO ChiTietHoaDon (MaChiTietHD, MaHoaDon, MaMon, SoLuong, DonGiaLucDat, GhiChuMon, ThoiGianTao) VALUES
(1, 1, 1, 1, 20000.00, NULL, GETDATE()),
(2, 1, 2, 1, 35000.00, N'Ít đá', GETDATE()),
(3, 2, 3, 1, 30000.00, N'Không đường', GETDATE());
GO
