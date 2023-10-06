package dathtph20511.poly.PNLib.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dathtph20511.poly.PNLib.DataBase.PhieuMuonDAO;
import dathtph20511.poly.PNLib.DataBase.SachDAO;
import dathtph20511.poly.PNLib.DataBase.ThanhVienDAO;
import dathtph20511.poly.PNLib.Model.PhieuMuon;
import dathtph20511.poly.PNLib.Model.Sach;
import dathtph20511.poly.PNLib.Model.ThanhVien;
import dathtph20511.poly.PNLib.R;

public class PhieuMuonRecycleAdapter extends RecyclerView.Adapter<PhieuMuonRecycleAdapter.userViewHolder> {
    private ArrayList<PhieuMuon> arrayList;
    private Context context;
    private PhieuMuonDAO phieuMuonDAO;
    private ThanhVienDAO thanhVienDAO;
    private SachDAO sachDAO;
    private Spinner spn_tenTV, spn_tenSach;

    public PhieuMuonRecycleAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<PhieuMuon> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
        phieuMuonDAO = new PhieuMuonDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);
        sachDAO = new SachDAO(context);
    }


    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phieumuon, parent, false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        PhieuMuon phieuMuon = arrayList.get(position);
        if (phieuMuon == null) {
            return;
        }
        holder.tv_maPhieu.setText(String.valueOf(phieuMuon.getMaPhieu()));
        ThanhVien thanhVien = thanhVienDAO.getID(phieuMuon.getMaTV());
        holder.tv_tenTV.setText(thanhVien.getTenTV());
        Sach sach = sachDAO.getID(phieuMuon.getMaSach());
        holder.tv_tenSach.setText(sach.getTenSach());
        holder.tv_tien.setText(String.valueOf(phieuMuon.getTienThue()));
        holder.tv_ngay.setText(new SimpleDateFormat("dd/MM/yyyy").format(phieuMuon.getNgayMuon()));
        holder.img_delete.setOnClickListener(v -> {
            dialogDelete(phieuMuon);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(phieuMuon);
        });
        if (phieuMuon.getTraSach() == 1) {
            holder.tv_traSach.setText("Đã trả sách");
            holder.tv_traSach.setTextColor(Color.parseColor("#2638D7"));
        } else {
            holder.tv_traSach.setText("Chưa trả sách");
            holder.tv_traSach.setTextColor(Color.parseColor("#FFDA1D0F"));
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public class userViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_maPhieu, tv_tenTV, tv_tenSach, tv_tien, tv_ngay, tv_traSach;
        private ImageView img_delete;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maPhieu = itemView.findViewById(R.id.tv_phieuMuon_ma);
            tv_tenTV = itemView.findViewById(R.id.tv_phieuMuon_tenTv);
            tv_tenSach = itemView.findViewById(R.id.tv_phieuMuon_tenSach);
            tv_tien = itemView.findViewById(R.id.tv_phieuMuon_tien);
            tv_ngay = itemView.findViewById(R.id.tv_phieuMuon_ngay);
            tv_traSach = itemView.findViewById(R.id.tv_phieuMuon_tinhTrang);
            img_delete = itemView.findViewById(R.id.img_phieuMuon_Xoa);
        }
    }
    private void dialogDelete(PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phieuMuonDAO.delete(phieuMuon);
                arrayList = phieuMuonDAO.getAllPhieuMuon();
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
    public void dialogUpdate(PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_phieumuon, null);

        TextView tv_ngayThue, tv_tienThue;
        TextInputEditText ed_maphieu;
        CheckBox chk_traSach;

        ed_maphieu = v.findViewById(R.id.ed_dialogPM_maPhieu);
        tv_ngayThue = v.findViewById(R.id.tv_dialogPM_ngay);
        tv_tienThue = v.findViewById(R.id.tv_dialogPM_tien);
        chk_traSach = v.findViewById(R.id.chk_traSach);
        spn_tenTV = v.findViewById(R.id.spn_dialogPM_tenTV);
        spn_tenSach = v.findViewById(R.id.spn_dialogPM_tenSach);

        thanhVienDAO = new ThanhVienDAO(context);
        sachDAO = new SachDAO(context);

        ed_maphieu.setText(String.valueOf(phieuMuon.getMaPhieu()));
        List<String> tenTV = new ArrayList<>();
        for (ThanhVien list : thanhVienDAO.getAllThanhVien()) {
            tenTV.add(list.getMaTV() + "." + list.getTenTV());
        }
        Spn_Adapter(spn_tenTV, tenTV);
        List<String> sach = new ArrayList<>();
        for (Sach listSach : sachDAO.getAllSach()) {
            sach.add(listSach.getMaSach() + "." + listSach.getTenSach());
        }
        Spn_Adapter(spn_tenSach, sach);

        for (int i = 0; i < tenTV.size(); i++) {
            String chuoi = tenTV.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if (phieuMuon.getMaTV() == Integer.parseInt(chuoi2[0])) {
                spn_tenTV.setSelection(i);
                Log.e("spn", phieuMuon.getMaTV() + "");
            }
        }
        for (int i = 0; i < tenTV.size(); i++) {
            String chuoi = sach.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if (phieuMuon.getMaSach() == Integer.parseInt(chuoi2[0])) {
                spn_tenSach.setSelection(i);
            }
        }
        tv_ngayThue.setText("Ngày thuê: " + new SimpleDateFormat("dd/MM/yyyy").format(phieuMuon.getNgayMuon()));
        spn_tenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sach sach1 = sachDAO.getID(split(spn_tenSach));
                tv_tienThue.setText(String.valueOf(sach1.getGiaThue()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setView(v);
        AlertDialog alertDialog = builder.create();

        v.findViewById(R.id.btn_dialogPM_luu).setOnClickListener(v1 -> {
            phieuMuonDAO = new PhieuMuonDAO(context);
            phieuMuon.setMaTV(split(spn_tenTV));
            phieuMuon.setMaSach(split(spn_tenSach));
            phieuMuon.setTienThue(Integer.parseInt(tv_tienThue.getText().toString()));
            phieuMuon.setNgayMuon(new Date());
            if (chk_traSach.isChecked()) {
                phieuMuon.setTraSach(1);
            } else {
                phieuMuon.setTraSach(0);
            }
            int kq = phieuMuonDAO.update(phieuMuon);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhât thành công", Toast.LENGTH_SHORT).show();
            }
            arrayList = phieuMuonDAO.getAllPhieuMuon();
            setData(arrayList);
            alertDialog.cancel();
        });
        v.findViewById(R.id.btn_dialogPM_huy).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }

    public void Spn_Adapter(Spinner spn, List<String> list) {
        ArrayAdapter<String> adapterSach = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        spn.setAdapter(adapterSach);
    }

    public int split(Spinner spn) {
        String chuoi = (String) spn.getSelectedItem();
        String[] chuoi2 = chuoi.split("\\.");
        return Integer.parseInt(chuoi2[0]);
    }
}
