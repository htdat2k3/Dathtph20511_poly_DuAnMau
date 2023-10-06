package dathtph20511.poly.PNLib.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.DataBase.ThanhVienDAO;
import dathtph20511.poly.PNLib.Model.ThanhVien;
import dathtph20511.poly.PNLib.R;

public class ThanhVienRecycleAdapter extends RecyclerView.Adapter<ThanhVienRecycleAdapter.userViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> arrayList;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienRecycleAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<ThanhVien> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
        thanhVienDAO = new ThanhVienDAO(context);
    }
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhvien,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        ThanhVien thanhVien = arrayList.get(position);
        if(thanhVien == null){
            return;
        }
        holder.tv_maTV.setText("Mã thành viên: "+thanhVien.getMaTV());
        holder.tv_tenTV.setText("Tên thành viên: "+thanhVien.getTenTV());
        holder.tv_ngaySinh.setText("Năm sinh: "+thanhVien.getNamSinh());
        holder.img_delete.setOnClickListener(v -> {
            dialogDelete(thanhVien);
        });
    }

    @Override
    public int getItemCount() {
        if(arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class userViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_maTV, tv_tenTV, tv_ngaySinh;
        private ImageView img_delete;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maTV = itemView.findViewById(R.id.tv_thanhVien_ma);
            tv_tenTV = itemView.findViewById(R.id.tv_thanhVien_ten);
            tv_ngaySinh = itemView.findViewById(R.id.tv_thanhVien_ngaySinh);
            img_delete = itemView.findViewById(R.id.img_tv_Xoa);
        }

    }
    private void dialogDelete(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                thanhVienDAO.delete(thanhVien);
                arrayList = thanhVienDAO.getAllThanhVien();
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
    public void dialogUpdate(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_thanhvien,null);
        TextInputEditText ed_maTV = v.findViewById(R.id.ed_dialogTV_maTV);
        TextInputEditText ed_tenTV = v.findViewById(R.id.ed_dialogTV_tenTV);
        TextInputEditText ed_ngaySinh = v.findViewById(R.id.ed_dialogTV_ngaySinh);
        Button btn_luu,btn_huy;
        btn_luu = v.findViewById(R.id.btn_dialogTV_luu);
        btn_huy = v.findViewById(R.id.btn_dialogTV_huy);
        ed_maTV.setText(String.valueOf(thanhVien.getMaTV()));
        ed_ngaySinh.setText(thanhVien.getNamSinh());
        ed_tenTV.setText(thanhVien.getTenTV());
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 ->{
            thanhVienDAO = new ThanhVienDAO(context);
            thanhVien.setTenTV(ed_tenTV.getText().toString());
            thanhVien.setNamSinh(ed_ngaySinh.getText().toString());
            int kq = thanhVienDAO.Insert(thanhVien);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
            alertDialog.cancel();
        });
        btn_huy.setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
        arrayList = thanhVienDAO.getAllThanhVien();
        setData(arrayList);
    }

}
