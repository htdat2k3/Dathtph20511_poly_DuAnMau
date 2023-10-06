package dathtph20511.poly.PNLib.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.Model.ThanhVien;

public class ThanhVienDAO {
    private Context mContext;
    private SQLiteDatabase db;
    private SQLite sqLite;

    public ThanhVienDAO(Context mContext){
        this.mContext = mContext;
        sqLite = new SQLite(mContext);
        db = sqLite.getWritableDatabase();
    }
    public int Insert(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("hoTen",thanhVien.getTenTV());
        values.put("namSinh",thanhVien.getNamSinh());
        long kq = db.insert("THANHVIEN",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<ThanhVien> getAllThanhVien(){
        Cursor cursor = db.rawQuery("SELECT *FROM THANHVIEN",null);
        ArrayList<ThanhVien> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(ThanhVien thanhVien){
        int kq = db.delete("THANHVIEN","maTV=?",new String[]{String.valueOf(thanhVien.getMaTV())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("tenTV",thanhVien.getTenTV());
        values.put("namSinh",thanhVien.getNamSinh());
        int kq = db.update("THANHVIEN",values,"maTV=?",new String[]{String.valueOf(thanhVien.getMaTV())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ThanhVien getID(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN WHERE maTV=?",new String[]{String.valueOf(id)});
        ArrayList<ThanhVien> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
        return list.get(0);
    }
}
