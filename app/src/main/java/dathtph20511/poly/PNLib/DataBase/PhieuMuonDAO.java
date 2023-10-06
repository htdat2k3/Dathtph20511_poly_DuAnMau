package dathtph20511.poly.PNLib.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dathtph20511.poly.PNLib.Model.PhieuMuon;
import dathtph20511.poly.PNLib.Model.Sach;

public class PhieuMuonDAO {
    private Context context;
    private SQLiteDatabase db;
    private SQLite sqLite;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    public PhieuMuonDAO(Context context){
        this.context = context;
        sqLite = new SQLite(context);
        db = sqLite.getWritableDatabase();
    }
    public int Insert(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();
        values.put("maTT",phieuMuon.getMaTT());
        values.put("maTV",phieuMuon.getMaTV());
        values.put("maSach",phieuMuon.getMaSach());
        values.put("tienThue",phieuMuon.getTienThue());
        values.put("ngay",format.format(phieuMuon.getNgayMuon()));
        values.put("traSach",phieuMuon.getTraSach());
        long kq = db.insert("PHIEUMUON",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<PhieuMuon> getAllPhieuMuon(){
        Cursor cursor = db.rawQuery("SELECT *FROM PHIEUMUON",null);
        ArrayList<PhieuMuon> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                try {
                    list.add(new PhieuMuon(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            format.parse(cursor.getString(5))
                            ,cursor.getInt(6)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(PhieuMuon phieuMuon){
        int kq = db.delete("PHIEUMUON","maPM=?",new String[]{String.valueOf(phieuMuon.getMaPhieu())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();
        values.put("maTT",phieuMuon.getMaTT());
        values.put("maTV",phieuMuon.getMaTV());
        values.put("maSach",phieuMuon.getMaSach());
        values.put("tienThue",phieuMuon.getTienThue());
        values.put("ngay",format.format(phieuMuon.getNgayMuon()));
        values.put("traSach",phieuMuon.getTraSach());
        int kq =db.update("PHIEUMUON",values,"maPM=?",new String[]{String.valueOf(phieuMuon.getMaPhieu())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<Sach> top10(){
        Cursor cursor = db.rawQuery("SELECT SACH.maSach,SACH.tenSach,SACH.giathue,COUNT(SACH.maSach) FROM SACH JOIN PHIEUMUON ON SACH.maSach=PHIEUMUON.maSach GROUP BY PHIEUMUON.maSach ORDER BY COUNT(SACH.maSach)DESC LIMIT 10 ",null);
        ArrayList<Sach> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int Doanhthu(String date1 ,String date2){
        Cursor cursor = db.rawQuery("SELECT SUM(tienThue)  FROM PHIEUMUON WHERE ngay BETWEEN ? AND ?",new String[]{date1,date2});
        int dt = 0;
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            dt = cursor.getInt(0);
        }
        return dt;
    }
}
