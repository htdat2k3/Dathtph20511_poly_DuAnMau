package dathtph20511.poly.PNLib.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dathtph20511.poly.PNLib.Adapter.PhieuMuonRecycleAdapter;
import dathtph20511.poly.PNLib.DataBase.PhieuMuonDAO;
import dathtph20511.poly.PNLib.DataBase.SachDAO;
import dathtph20511.poly.PNLib.DataBase.ThanhVienDAO;
import dathtph20511.poly.PNLib.Model.PhieuMuon;
import dathtph20511.poly.PNLib.Model.Sach;
import dathtph20511.poly.PNLib.Model.ThanhVien;
import dathtph20511.poly.PNLib.R;


public class PhieuMuonFragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private PhieuMuonRecycleAdapter adapter;
    private ArrayList<PhieuMuon> list = new ArrayList<>();
    private PhieuMuonDAO phieuMuonDAO;
    private ThanhVienDAO thanhVienDAO;
    private SachDAO sachDAO;
    private Spinner spn_tenTV,spn_tenSach;


    public PhieuMuonFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PhieuMuonFragment newInstance(String param1, String param2) {
        PhieuMuonFragment fragment = new PhieuMuonFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_PT);
        actionButton = view.findViewById(R.id.floatingaction_PT);
        actionButton.setOnClickListener(this);
    }
    public void diaLog_PM(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_phieumuon,null);
        TextView tv_ngayThue,tv_tienThue;
        CheckBox chk_traSach;
        tv_ngayThue = v.findViewById(R.id.tv_dialogPM_ngay);
        tv_tienThue = v.findViewById(R.id.tv_dialogPM_tien);
        chk_traSach = v.findViewById(R.id.chk_traSach);
        spn_tenTV = v.findViewById(R.id.spn_dialogPM_tenTV);
        spn_tenSach = v.findViewById(R.id.spn_dialogPM_tenSach);
        thanhVienDAO = new ThanhVienDAO(getActivity());
        sachDAO = new SachDAO(getActivity());
        List<String> tenTV = new ArrayList<>();
        for(ThanhVien list : thanhVienDAO.getAllThanhVien()) {
            tenTV.add(list.getMaTV()+"."+list.getTenTV());
        }
        Spn_Adapter(spn_tenTV,tenTV);
        List<String> sach = new ArrayList<>();
        for(Sach listSach: sachDAO.getAllSach() ){
            sach.add(listSach.getMaSach()+"."+listSach.getTenSach());
        }
        Spn_Adapter(spn_tenSach,sach);

        tv_ngayThue.setText("Ngày thuê: "+new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
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
            phieuMuonDAO = new PhieuMuonDAO(getActivity());
            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaTV(split(spn_tenTV));
            phieuMuon.setMaSach(split(spn_tenSach));
            phieuMuon.setTienThue(Integer.parseInt(tv_tienThue.getText().toString()));
            phieuMuon.setNgayMuon(new Date());
            if(chk_traSach.isChecked()){
                phieuMuon.setTraSach(1);
            }else{
                phieuMuon.setTraSach(0);
            }
            int kq = phieuMuonDAO.Insert(phieuMuon);
            if (kq == -1) {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            onResume();
            alertDialog.cancel();
        });
        v.findViewById(R.id.btn_dialogPM_huy).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
    public void onResume(){
        super.onResume();
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        list.clear();
        list = phieuMuonDAO.getAllPhieuMuon();
        adapter = new PhieuMuonRecycleAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingaction_PT:
                diaLog_PM();
                break;
        }

    }
    public void Spn_Adapter(Spinner spn, List<String> list){
        ArrayAdapter<String> adapterSach = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
        spn.setAdapter(adapterSach);
    }
    public int split(Spinner spn){
        String chuoi = (String)spn.getSelectedItem();
        String[] chuoi2 = chuoi.split("\\.");
        return  Integer.parseInt(chuoi2[0]);
    }

}