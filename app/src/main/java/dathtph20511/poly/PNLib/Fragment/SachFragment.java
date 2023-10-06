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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import dathtph20511.poly.PNLib.Adapter.SachRecycleAdapter;
import dathtph20511.poly.PNLib.DataBase.LoaiSachDAO;
import dathtph20511.poly.PNLib.DataBase.SachDAO;
import dathtph20511.poly.PNLib.Model.LoaiSach;
import dathtph20511.poly.PNLib.Model.Sach;
import dathtph20511.poly.PNLib.R;


public class SachFragment extends Fragment implements  View.OnClickListener{
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ArrayList<Sach> list = new ArrayList<>();
    private SachDAO sachDAO;
    private SachRecycleAdapter adapter;
    private LoaiSachDAO loaiSachDAO;
    public SachFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SachFragment newInstance( ) {
        SachFragment fragment = new SachFragment();

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
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_S);
        actionButton = view.findViewById(R.id.floatingaction_S);
        actionButton.setOnClickListener(this);
    }
    public void dialog_Sach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sach, null);
        TextInputEditText ed_maSach,ed_tenSach,ed_giaSach;
        Spinner spn_loaiSach;
        Button btn_luu,btn_huy;
        ed_tenSach = v.findViewById(R.id.ed_dialogSach_tenSach);
        ed_giaSach = v.findViewById(R.id.ed_dialogSach_giaSach);
        spn_loaiSach = v.findViewById(R.id.spn_dialogSach_LS);
        btn_luu = v.findViewById(R.id.btn_dialogSach_luu);
        btn_huy = v.findViewById(R.id.btn_dialogSach_huy);
        loaiSachDAO = new LoaiSachDAO(getActivity());
        List<String> loaiSach =  new ArrayList<>();
        for(LoaiSach listLoaiSach: loaiSachDAO.getAllLoaiSach()){
            loaiSach.add(listLoaiSach.getMaLoai()+"."+listLoaiSach.getTenLoai());
        }
        ArrayAdapter<String> adapterLoaiSach = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,loaiSach);
        spn_loaiSach.setAdapter(adapterLoaiSach);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 -> {
            Sach sach = new Sach();
            String LoaiSach = (String)spn_loaiSach.getSelectedItem();
            String[] maloai= LoaiSach.split("\\.");
            sach.setTenSach(ed_tenSach.getText().toString());
            sach.setGiaThue(Integer.parseInt(ed_giaSach.getText().toString()));
            sach.setLoaiSach(Integer.parseInt(maloai[0]));
            int kq = sachDAO.Insert(sach);
            if (kq == -1) {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            onResume();
            alertDialog.cancel();
        });
        btn_huy.setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
    public void onResume() {
        super.onResume();
        sachDAO = new SachDAO(getActivity());
        list.clear();
        list = sachDAO.getAllSach();
        adapter = new SachRecycleAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingaction_S:
                dialog_Sach();
                break;
        }
    }

}