package dathtph20511.poly.PNLib.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.Model.LoaiSach;

public class LoaiSachDAO {
    //tạo biến môi trường
    private Context context;
    private SQLiteDatabase db;
    private SQLite sqLite;

    public LoaiSachDAO(Context context) {
        this.context = context;
        sqLite = new SQLite(context);
        // cho phép đọc dư liệu
        db = sqLite.getWritableDatabase();
    }

    public int Insert(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        long kq = db.insert("LOAISACH", null, values);
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public ArrayList<LoaiSach> getAllLoaiSach() {
        Cursor cursor = db.rawQuery("SELECT *FROM LOAISACH", null);
        ArrayList<LoaiSach> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int delete(LoaiSach loaiSach) {
        int kq = db.delete("LOAISACH", "maLoai=?", new String[]{String.valueOf(loaiSach.getMaLoai())});
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public int update(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("maLoai", loaiSach.getMaLoai());
        values.put("tenLoai", loaiSach.getTenLoai());
        int kq = db.update("LOAISACH", values, "maLoai=?", new String[]{String.valueOf(loaiSach.getMaLoai())});
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public LoaiSach getID(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM LOAISACH WHERE maLoai= ?", new String[]{String.valueOf(id)});
        Log.e("MaLoai", id + "");
        ArrayList<LoaiSach> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
        return list.get(0);
    }
}
