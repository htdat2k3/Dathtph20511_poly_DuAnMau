package dathtph20511.poly.PNLib.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.Model.Sach;

public class SachDAO {
    private Context mContext;
    private SQLiteDatabase db;
    private SQLite sqLite;
    public SachDAO(Context context){
        this.mContext = context;
        sqLite = new SQLite(mContext);
        db =  sqLite.getWritableDatabase();
    }
    public int Insert(Sach sach){
        ContentValues values = new ContentValues();
        values.put("tenSach",sach.getTenSach());
        values.put("giaThue",sach.getGiaThue());
        values.put("maLoai",sach.getLoaiSach());
        long kq = db.insert("SACH",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<Sach> getAllSach(){
        Cursor cursor = db.rawQuery("SELECT *FROM SACH",null);
        ArrayList<Sach> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(Sach sach){
        int kq = db.delete("SACH","maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(Sach sach){
        ContentValues values = new ContentValues();
        values.put("tenSach",sach.getTenSach());
        values.put("giaThue",sach.getGiaThue());
        values.put("maLoai",sach.getLoaiSach());
        int kq = db.update("SACH",values,"maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public Sach getID(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM SACH WHERE maSach=?",new String[]{String.valueOf(id)});
        ArrayList<Sach> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
        return list.get(0);
    }
}
