package com.project.app.model;

import java.time.LocalDateTime;

public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private String chucVu;
    private String soDienThoai;
    private String email;
    private LocalDateTime thoiGianTao;
    private LocalDateTime thoiGianCapNhat;

    // Constructors
    public NhanVien() {}

    public NhanVien(String maNhanVien, String tenNhanVien, String chucVu, 
                   String soDienThoai, String email) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    // Getters and Setters
    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }
    
    public String getTenNhanVien() { return tenNhanVien; }
    public void setTenNhanVien(String tenNhanVien) { this.tenNhanVien = tenNhanVien; }
    
    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }
    
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    
    public LocalDateTime getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(LocalDateTime thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }
}
