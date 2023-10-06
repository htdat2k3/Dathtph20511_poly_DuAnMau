package dathtph20511.poly.PNLib.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dathtph20511.poly.PNLib.Adapter.ThanhVienRecycleAdapter;
import dathtph20511.poly.PNLib.DataBase.ThanhVienDAO;
import dathtph20511.poly.PNLib.Model.ThanhVien;
import dathtph20511.poly.PNLib.R;


public class ThanhVienFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ArrayList<ThanhVien> list = new ArrayList<>();
    private ThanhVienRecycleAdapter adapter;
    private int day,month,year;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ThanhVienFragment newInstance(String param1, String param2) {
        ThanhVienFragment fragment = new ThanhVienFragment();

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
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_TV);
        actionButton = view.findViewById(R.id.floatingactionTV);
        actionButton.setOnClickListener(this);
    }
    public void dialog_TV(){
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_thanhvien,null);
        TextInputEditText ed_tenTV = v.findViewById(R.id.ed_dialogTV_tenTV);
        TextInputEditText ed_ngaySinh = v.findViewById(R.id.ed_dialogTV_ngaySinh);
        ed_ngaySinh.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    calendar.set(i,i1,i2);
                    ed_ngaySinh.setText(simpleDateFormat.format(calendar.getTime()));
                }
            },year,month,day);
            datePickerDialog.show();
        });
        Button btn_luu,btn_huy ;
        btn_luu = v.findViewById(R.id.btn_dialogTV_luu);
        btn_huy= v.findViewById(R.id.btn_dialogTV_huy);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 ->{
            thanhVienDAO = new ThanhVienDAO(getActivity());
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setTenTV(ed_tenTV.getText().toString());
            thanhVien.setNamSinh(ed_ngaySinh.getText().toString());
            int kq = thanhVienDAO.Insert(thanhVien);
            if (kq == -1) {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            onResume();
            alertDialog.cancel();
        });
        btn_huy.setOnClickListener(v2->{
            alertDialog.cancel();
        });

        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        thanhVienDAO = new ThanhVienDAO(getActivity());
        list.clear();
        list= thanhVienDAO.getAllThanhVien();
        adapter = new ThanhVienRecycleAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.floatingactionTV:
                dialog_TV();
                break;
        }
    }
}