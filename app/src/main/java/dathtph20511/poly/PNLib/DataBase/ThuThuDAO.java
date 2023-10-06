package dathtph20511.poly.PNLib.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.Model.ThuThu;

public class ThuThuDAO {
    private Context context;
    private SQLiteDatabase db;
    private SQLite sqLite;

    public ThuThuDAO(Context context) {
        this.context = context;
        sqLite = new SQLite(context);
        db = sqLite.getWritableDatabase();
    }
    public int Insert(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put("maTT",thuThu.getMaTT());
        values.put("hoTen",thuThu.getHoTen());
        values.put("matKhau",thuThu.getMatKhau());
        long kq = db.insert("THUTHU",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;

    }
    public ArrayList<ThuThu> getAllThuThu(){
        Cursor cursor = db.rawQuery("SELECT *FROM THUTHU",null);
        ArrayList<ThuThu> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new ThuThu(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(ThuThu thuThu){
        int kq = db.delete("THUTHU","maTT=?",new String[]{String.valueOf(thuThu.getMaTT())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put("matKhau",thuThu.getMatKhau());
        int kq = db.update("THUTHU",values,"maTT=?",new String[]{String.valueOf(thuThu.getMaTT())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ThuThu getId(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE maTT=?",new String[]{id});
        ArrayList<ThuThu> list = new ArrayList<>();
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            list.add(new ThuThu(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        return list.get(0);
    }
}
