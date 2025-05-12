package com.project.app.model;

import java.time.LocalDateTime;

public class LoaiMon {
    private int maLoaiMon;
    private String tenLoaiMon;
    private String moTa;
    private String phanLoai;
    private String hinhAnh;
    private LocalDateTime thoiGianTao;
    private LocalDateTime thoiGianCapNhat;

    // Constructors
    public LoaiMon() {}

    public LoaiMon(int maLoaiMon, String tenLoaiMon, String moTa, String phanLoai, String hinhAnh) {
        this.maLoaiMon = maLoaiMon;
        this.tenLoaiMon = tenLoaiMon;
        this.moTa = moTa;
        this.phanLoai = phanLoai;
        this.hinhAnh = hinhAnh;
    }

    // Getters and Setters
    public int getMaLoaiMon() { return maLoaiMon; }
    public void setMaLoaiMon(int maLoaiMon) { this.maLoaiMon = maLoaiMon; }

    public String getTenLoaiMon() { return tenLoaiMon; }
    public void setTenLoaiMon(String tenLoaiMon) { this.tenLoaiMon = tenLoaiMon; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getPhanLoai() { return phanLoai; }
    public void setPhanLoai(String phanLoai) { this.phanLoai = phanLoai; }

    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }

    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }

    public LocalDateTime getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(LocalDateTime thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }

    @Override
    public String toString() {
        return "LoaiMon{" +
                "maLoaiMon=" + maLoaiMon +
                ", tenLoaiMon='" + tenLoaiMon + '\'' +
                ", moTa='" + moTa + '\'' +
                ", phanLoai='" + phanLoai + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", thoiGianTao=" + thoiGianTao +
                ", thoiGianCapNhat=" + thoiGianCapNhat +
                '}';
    }
}
