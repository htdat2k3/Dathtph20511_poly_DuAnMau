package dathtph20511.poly.PNLib.Model;

import java.util.Date;

public class PhieuMuon {
    private int maPhieu;
    private String maTT;
    private int maTV;
    private int maSach;
    private int tienThue;
    private Date ngayMuon;
    private int traSach;
//    private String gioTraSach;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPhieu, String maTT, int maTV, int maSach, int tienThue, Date ngayMuon, int traSach) {
        this.maPhieu = maPhieu;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
//        this.gioTraSach=gioTraSach;
    }

    public int getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }
}
