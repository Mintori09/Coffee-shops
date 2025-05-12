package com.project.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ChiTietHoaDon {
    private int maChiTietHD;
    private int maHoaDon;
    private int maMon;
    private int soLuong;
    private BigDecimal donGiaLucDat;
    private BigDecimal thanhTien;
    private String ghiChuMon;
    private LocalDateTime thoiGianTao;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int maHoaDon, int maMon, int soLuong, BigDecimal donGiaLucDat, 
                        BigDecimal thanhTien, String ghiChuMon) {
        this.maHoaDon = maHoaDon;
        this.maMon = maMon;
        this.soLuong = soLuong;
        this.donGiaLucDat = donGiaLucDat;
        this.thanhTien = thanhTien;
        this.ghiChuMon = ghiChuMon;
    }

    // Getters and Setters
    public int getMaChiTietHD() {
        return maChiTietHD;
    }

    public void setMaChiTietHD(int maChiTietHD) {
        this.maChiTietHD = maChiTietHD;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGiaLucDat() {
        return donGiaLucDat;
    }

    public void setDonGiaLucDat(BigDecimal donGiaLucDat) {
        this.donGiaLucDat = donGiaLucDat;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getGhiChuMon() {
        return ghiChuMon;
    }

    public void setGhiChuMon(String ghiChuMon) {
        this.ghiChuMon = ghiChuMon;
    }

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "maChiTietHD=" + maChiTietHD +
                ", maHoaDon=" + maHoaDon +
                ", maMon=" + maMon +
                ", soLuong=" + soLuong +
                ", donGiaLucDat=" + donGiaLucDat +
                ", thanhTien=" + thanhTien +
                ", ghiChuMon='" + ghiChuMon + '\'' +
                ", thoiGianTao=" + thoiGianTao +
                '}';
    }
}
