package dathtph20511.poly.PNLib.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import dathtph20511.poly.PNLib.DataBase.LoaiSachDAO;
import dathtph20511.poly.PNLib.DataBase.SachDAO;
import dathtph20511.poly.PNLib.Model.LoaiSach;
import dathtph20511.poly.PNLib.Model.Sach;
import dathtph20511.poly.PNLib.R;

public class SachRecycleAdapter extends RecyclerView.Adapter<SachRecycleAdapter.userViewHolder> {
    private Context context;
    private ArrayList<Sach> arrayList;
    private LoaiSachDAO loaiSachDAO;
    private SachDAO sachDAO;
    public SachRecycleAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<Sach> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
        sachDAO = new SachDAO(context);
        loaiSachDAO = new LoaiSachDAO(context);

    }
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        Sach sach = arrayList.get(position);
        if(sach == null){
            return;
        }
        holder.tv_maSach.setText("Mã sách: "+sach.getMaSach());
        holder.tv_tenSach.setText("Tên sách: "+sach.getTenSach());
        holder.tv_giaTien.setText("Giá tiền: "+ String.valueOf(sach.getGiaThue()));
        LoaiSach loaiSach = loaiSachDAO.getID(sach.getLoaiSach());
        holder.tv_loaiSach.setText("Loại sách: "+loaiSach.getTenLoai());
        holder.img_delete.setOnClickListener(v -> {
            dialogDelete(sach);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(sach);
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
        private TextView tv_maSach, tv_tenSach, tv_giaTien, tv_loaiSach;
        private ImageView img_delete;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maSach = itemView.findViewById(R.id.tv_sach_maSach);
            tv_loaiSach = itemView.findViewById(R.id.tv_sach_loaiSach);
            tv_tenSach = itemView.findViewById(R.id.tv_sach_tenSach);
            tv_giaTien = itemView.findViewById(R.id.tv_sach_gia);
            img_delete = itemView.findViewById(R.id.img_sach_Xoa);
        }

    }
    private void dialogDelete(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sachDAO.delete(sach);
                arrayList = sachDAO.getAllSach();
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
    public void dialogUpdate(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sach, null);
        TextInputEditText ed_maSach,ed_tenSach,ed_giaSach;
        Spinner spn_loaiSach;
        Button btn_luu,btn_huy;
        ed_maSach = v.findViewById(R.id.ed_dialogSach_maSach);
        ed_tenSach = v.findViewById(R.id.ed_dialogSach_tenSach);
        ed_giaSach = v.findViewById(R.id.ed_dialogSach_giaSach);
        spn_loaiSach = v.findViewById(R.id.spn_dialogSach_LS);
        btn_luu = v.findViewById(R.id.btn_dialogSach_luu);
        btn_huy = v.findViewById(R.id.btn_dialogSach_huy);
        loaiSachDAO = new LoaiSachDAO(context);
        List<String> loaiSach =  new ArrayList<>();
        for(LoaiSach listLoaiSach: loaiSachDAO.getAllLoaiSach()){
            loaiSach.add(listLoaiSach.getMaLoai()+"."+listLoaiSach.getTenLoai());
        }
        ArrayAdapter<String> adapterLoaiSach = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,loaiSach);
        spn_loaiSach.setAdapter(adapterLoaiSach);


        ed_maSach.setText(String.valueOf(sach.getMaSach()));
        ed_tenSach.setText(sach.getTenSach());
        ed_giaSach.setText(String.valueOf(sach.getGiaThue()));
        for(int i =0;i<loaiSach.size();i++){
            String chuoi = loaiSach.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if(sach.getLoaiSach()==Integer.parseInt(chuoi2[0])){
                spn_loaiSach.setSelection(i);
            }
        }
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 -> {
            String LoaiSach = (String)spn_loaiSach.getSelectedItem();
            String[] maloai= LoaiSach.split("\\.");
            sach.setTenSach(ed_tenSach.getText().toString());
            sach.setGiaThue(Integer.parseInt(ed_giaSach.getText().toString()));
            sach.setLoaiSach(Integer.parseInt(maloai[0]));
            int kq = sachDAO.Insert(sach);
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
        arrayList = sachDAO.getAllSach();
        setData(arrayList);
    }}
