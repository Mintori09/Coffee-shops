CREATE DATABASE IF NOT EXISTS CoffeeShop;
USE CoffeeShop;


/*M!999999\- enable the sandbox mode */
-- MariaDB dump 10.19-11.7.2-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: CoffeeShop
-- ------------------------------------------------------
-- Server version	11.7.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `ChiTietHoaDon`
--

DROP TABLE IF EXISTS `ChiTietHoaDon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ChiTietHoaDon` (
  `MaChiTietHD` int(11) NOT NULL,
  `MaHoaDon` int(11) DEFAULT NULL,
  `MaMon` int(11) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DonGiaLucDat` decimal(10,2) DEFAULT NULL,
  `ThanhTien` decimal(10,2) DEFAULT NULL,
  `GhiChuMon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_uca1400_ai_ci DEFAULT NULL,
  `ThoiGianTao` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`MaChiTietHD`),
  KEY `MaHoaDon` (`MaHoaDon`),
  KEY `MaMon` (`MaMon`),
  CONSTRAINT `ChiTietHoaDon_ibfk_1` FOREIGN KEY (`MaHoaDon`) REFERENCES `HoaDon` (`MaHoaDon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChiTietHoaDon`
--

LOCK TABLES `ChiTietHoaDon` WRITE;
/*!40000 ALTER TABLE `ChiTietHoaDon` DISABLE KEYS */;
INSERT INTO `ChiTietHoaDon` VALUES
(1,1,1,1,20000.00,'','2025-05-12 13:52:11','2025-05-12 13:52:11'),
(2,1,2,1,35000.00,'Ít đá','2025-05-12 13:52:11','2025-05-12 13:52:11'),
(3,2,3,1,30000.00,'Không đường','2025-05-12 13:52:11','2025-05-12 13:52:11');
/*!40000 ALTER TABLE `ChiTietHoaDon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HoaDon`
--

DROP TABLE IF EXISTS `HoaDon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `HoaDon` (
  `MaHoaDon` int(11) NOT NULL,
  `MaNhanVien` int(11) DEFAULT NULL,
  `ThoiGianThanhToan` datetime DEFAULT NULL,
  `TongTienPhaiTra` decimal(10,2) DEFAULT NULL,
  `TrangThai` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_uca1400_ai_ci DEFAULT NULL,
  `GhiChu` text DEFAULT NULL,
  `ThoiGianTao` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`MaHoaDon`),
  KEY `MaNhanVien` (`MaNhanVien`),
  CONSTRAINT `HoaDon_ibfk_1` FOREIGN KEY (`MaNhanVien`) REFERENCES `NhanVien` (`MaNhanVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HoaDon`
--

LOCK TABLES `HoaDon` WRITE;
/*!40000 ALTER TABLE `HoaDon` DISABLE KEYS */;
INSERT INTO `HoaDon` VALUES
(1,1,'2025-05-12 13:52:11',55000.00,'Đã thanh toán','Khách thanh toán tiền mặt','2025-05-12 13:52:11'),
(2,2,'2025-05-12 13:52:11',30000.00,'Chưa thanh toán','','2025-05-12 13:52:11');
/*!40000 ALTER TABLE `HoaDon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LoaiMon`
--

DROP TABLE IF EXISTS `LoaiMon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `LoaiMon` (
  `MaLoaiMon` int(11) NOT NULL,
  `TenLoaiMon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_uca1400_ai_ci DEFAULT NULL,
  `MoTa` text DEFAULT NULL,
  `PhanLoai` text DEFAULT NULL,
  `HinhAnh` varchar(100) DEFAULT NULL,
  `ThoiGianTao` datetime DEFAULT current_timestamp(),
  `ThoiGianCapNhat` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`MaLoaiMon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LoaiMon`
--

LOCK TABLES `LoaiMon` WRITE;
/*!40000 ALTER TABLE `LoaiMon` DISABLE KEYS */;
INSERT INTO `LoaiMon` VALUES
(1,'Cà phê sữa','Các loại cà phê truyền thống','Cà phê','images/milk_coffee.jpg','2025-05-12 13:52:11','2025-05-12 13:52:11'),
(2,'Sinh tố','Sinh tố trái cây tươi','Sinh tố','images/sinh_to.jpg','2025-05-12 13:52:11','2025-05-12 13:52:11'),
(3,'Trà sữa','Trà kết hợp với sữa và topping','Trà sữa','images/milk_tea.jpg','2025-05-12 13:52:11','2025-05-12 13:52:11');
/*!40000 ALTER TABLE `LoaiMon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NhanVien`
--

DROP TABLE IF EXISTS `NhanVien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `NhanVien` (
  `MaNhanVien` int(11) NOT NULL,
  `TenNhanVien` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_uca1400_ai_ci DEFAULT NULL,
  `SoDienThoai` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_uca1400_ai_ci DEFAULT NULL,
  `Email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_uca1400_ai_ci DEFAULT NULL,
  `ChucVu` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_uca1400_ai_ci DEFAULT NULL,
  `ThoiGianTao` datetime DEFAULT current_timestamp(),
  `ThoiGianCapNhat` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`MaNhanVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NhanVien`
--

LOCK TABLES `NhanVien` WRITE;
/*!40000 ALTER TABLE `NhanVien` DISABLE KEYS */;
INSERT INTO `NhanVien` VALUES
(1,'Nguyễn Văn A','0909123456','a@example.com','Thu ngân','2025-05-12 13:52:11','2025-05-12 13:52:11'),
(2,'Trần Thị B','0912345678','b@example.com','Pha chế','2025-05-12 13:52:11','2025-05-12 13:52:11');
/*!40000 ALTER TABLE `NhanVien` ENABLE KEYS */;
UNLOCK TABLES;

UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-05-12 13:56:46
