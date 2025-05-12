package com.project.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoaDon {
    private int maHoaDon;
    private String maNhanVien;
    private LocalDateTime thoiGianThanhToan;
    private BigDecimal tongTienPhaiTra;
    private String trangThai;
    private String ghiChu;
    private LocalDateTime thoiGianTao;
    private String tenNhanVien; // For display purposes

    // Constructors
    public HoaDon() {}

    public HoaDon(int maHoaDon, String maNhanVien, LocalDateTime thoiGianThanhToan,
                  BigDecimal tongTienPhaiTra, String trangThai, String ghiChu) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.thoiGianThanhToan = thoiGianThanhToan;
        this.tongTienPhaiTra = tongTienPhaiTra;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Getters and Setters
    public int getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(int maHoaDon) { this.maHoaDon = maHoaDon; }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public LocalDateTime getThoiGianThanhToan() { return thoiGianThanhToan; }
    public void setThoiGianThanhToan(LocalDateTime thoiGianThanhToan) { this.thoiGianThanhToan = thoiGianThanhToan; }

    public BigDecimal getTongTienPhaiTra() { return tongTienPhaiTra; }
    public void setTongTienPhaiTra(BigDecimal tongTienPhaiTra) { this.tongTienPhaiTra = tongTienPhaiTra; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }

    public String getTenNhanVien() { return tenNhanVien; }
    public void setTenNhanVien(String tenNhanVien) { this.tenNhanVien = tenNhanVien; }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon=" + maHoaDon +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", tenNhanVien='" + tenNhanVien + '\'' +
                ", thoiGianThanhToan=" + thoiGianThanhToan +
                ", tongTienPhaiTra=" + tongTienPhaiTra +
                ", trangThai='" + trangThai + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", thoiGianTao=" + thoiGianTao +
                '}';
    }
}
