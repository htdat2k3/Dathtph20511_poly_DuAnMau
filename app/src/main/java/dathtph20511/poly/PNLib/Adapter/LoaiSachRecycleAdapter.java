package dathtph20511.poly.PNLib.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.DataBase.LoaiSachDAO;
import dathtph20511.poly.PNLib.Model.LoaiSach;
import dathtph20511.poly.PNLib.R;

public class LoaiSachRecycleAdapter extends RecyclerView.Adapter<LoaiSachRecycleAdapter.userViewHolder> {
    //môi trường (màn hình hành động)
    private Context context;
    //Đổ dữ liệu ra màn hình
    private ArrayList<LoaiSach> arrayList;
    //database
    private LoaiSachDAO loaiSachDAO;
    public LoaiSachRecycleAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<LoaiSach> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
        loaiSachDAO = new LoaiSachDAO(context);
    }
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisach,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        LoaiSach loaiSach = arrayList.get(position);
        if(loaiSach == null){
            return;
        }
        holder.tv_maLoai.setText("Mã loại: "+loaiSach.getMaLoai());
        holder.tv_tenLoai.setText("Tên loại: "+loaiSach.getTenLoai());
        holder.img_delete.setOnClickListener(v -> {
            dialogDelete(loaiSach);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(loaiSach);
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class userViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tenLoai,tv_maLoai;
        private ImageView img_delete;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maLoai = itemView.findViewById(R.id.tv_loaiSach_maLoai);
            tv_tenLoai = itemView.findViewById(R.id.tv_loaiSach_tenLoai);
            img_delete = itemView.findViewById(R.id.img_loaiSach_xoa);
        }
    }

    private void dialogDelete(LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loaiSachDAO.delete(loaiSach);
                arrayList = loaiSachDAO.getAllLoaiSach();
                setData(arrayList);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
    @SuppressLint("MissingInflatedId")
    public void dialogUpdate(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_loaisach,null);
        TextInputEditText ed_tenLoai = v.findViewById(R.id.ed_dialogLS_tenLS);
        TextInputEditText ed_maLoai = v.findViewById(R.id.ed_dialogLS_maLS);
        ed_maLoai.setText(String.valueOf(loaiSach.getMaLoai()));
        ed_tenLoai.setText(String.valueOf(loaiSach.getTenLoai()));
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btn_dialogLS_luu).setOnClickListener(v1 ->{
            loaiSachDAO = new LoaiSachDAO(context);
            loaiSach.setTenLoai(ed_tenLoai.getText().toString());
            int kq = loaiSachDAO.update(loaiSach);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
            alertDialog.cancel();
        });
        v.findViewById(R.id.btn_dialogLS_huy).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
        arrayList = loaiSachDAO.getAllLoaiSach();
        setData(arrayList);
    }
}
