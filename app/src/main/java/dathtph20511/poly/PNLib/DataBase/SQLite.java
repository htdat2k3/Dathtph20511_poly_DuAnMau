package dathtph20511.poly.PNLib.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {
    public static final String SQL_PhieuMuon = "create table  PHIEUMUON (\n" +
            "           \tmaPM INTEGER primary key AUTOINCREMENT, \n" +
            "            maTT TEXT  REFERENCES THUTHU(maTT), \n" +
            "           \tmaTV INTEGER REFERENCES THANHVIEN(maTV),\n" +
            "            maSach INTEGER REFERENCES SACH(maSach),\n" +
            "           \ttienThue INTEGER not null,\n" +
            "            ngay DATE not null,\n" +
            "gioMuaHang_Ph20511 TEXT not null,\n"+
            "            traSach INTEGER not null );";
    public static final String SQL_LoaiSach = "create table  LOAISACH(\n" +
            "              maLoai INTEGER primary key AUTOINCREMENT,\n" +
            "               tenLoai TEXT not null);";
    public static final String SQL_ThuThu = "create table THUTHU ( \n" +
            "                    maTT TEXT primary key,\n" +
            "                    hoTen TEXT not null,\n" +
            "                    matKhau TEXT not null);";
    public static final String INSERT_THUTHU="INSERT INTO THUTHU(maTT,hoTen,matKhau)VALUES('admin','admin','1234')";
    public static final String SQL_Sach = "create table SACH(\n" +
            "                    maSach INTEGER primary key AUTOINCREMENT, \n" +
            "                    tenSach TEXT not null, \n" +
            "                    giaThue INTEGER not null, \n" +
            "                    maLoai INTEGER REFERENCES LOAISACH(maLoai));";
    public static final  String SQL_ThanhVien ="create table THANHVIEN(\n" +
            "                    maTV INTEGER primary key AUTOINCREMENT, \n" +
            "                    hoTen TEXT not null, \n" +
            "                    namSinh TEXT not null);";



    public SQLite(@Nullable Context context) {
        super(context, "QLTV", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_PhieuMuon);
        db.execSQL(SQL_LoaiSach);
        db.execSQL(SQL_ThuThu);
        db.execSQL(SQL_Sach);
        db.execSQL(SQL_ThanhVien);
        db.execSQL(INSERT_THUTHU);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
        db.execSQL("DROP TABLE IF EXISTS LOAISACH");
        db.execSQL("DROP TABLE IF EXISTS THUTHU");
        db.execSQL("DROP TABLE IF EXISTS SACH");
        db.execSQL("DROP TABLE IF EXISTS THANHVIEN");

        onCreate(db);
    }
}
