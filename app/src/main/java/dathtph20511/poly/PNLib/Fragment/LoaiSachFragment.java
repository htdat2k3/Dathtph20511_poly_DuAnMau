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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.Adapter.LoaiSachRecycleAdapter;
import dathtph20511.poly.PNLib.DataBase.LoaiSachDAO;
import dathtph20511.poly.PNLib.Model.LoaiSach;
import dathtph20511.poly.PNLib.R;


public class LoaiSachFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private LoaiSachDAO loaiSachDAO;
    private LoaiSachRecycleAdapter adapter;
    private ArrayList<LoaiSach> list = new ArrayList<>();

    public LoaiSachFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoaiSachFragment newInstance( ) {
        LoaiSachFragment fragment = new LoaiSachFragment();

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
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_LS);
        actionButton = view.findViewById(R.id.floatingaction_LS);

        actionButton.setOnClickListener(this);
    }
    public void dialog_LS(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_loaisach,null);
        TextInputEditText ed_tenLoai = v.findViewById(R.id.ed_dialogLS_tenLS);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btn_dialogLS_luu).setOnClickListener(v1 ->{
            loaiSachDAO = new LoaiSachDAO(getActivity());
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setTenLoai(ed_tenLoai.getText().toString());
            int kq = loaiSachDAO.Insert(loaiSach);
            if (kq == -1) {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            onResume();
            alertDialog.cancel();
        });
        v.findViewById(R.id.btn_dialogLS_huy).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });

        alertDialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingaction_LS:
                dialog_LS();
                break;
        }
    }
    public void onResume(){
        super.onResume();
        loaiSachDAO= new LoaiSachDAO(getActivity());
        list.clear();
        list = loaiSachDAO.getAllLoaiSach();
        adapter = new LoaiSachRecycleAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}