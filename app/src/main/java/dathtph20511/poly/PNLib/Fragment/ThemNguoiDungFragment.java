package dathtph20511.poly.PNLib.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import dathtph20511.poly.PNLib.DataBase.ThuThuDAO;
import dathtph20511.poly.PNLib.Model.ThuThu;
import dathtph20511.poly.PNLib.R;


public class ThemNguoiDungFragment extends Fragment {
    private TextInputEditText ed_maTT,ed_hoTen,ed_matKhau,ed_nhapLaiMK;
    private Button btn_them,btn_huy;
    private ThuThuDAO thuThuDAO;


    public ThemNguoiDungFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ThemNguoiDungFragment newInstance(String param1, String param2) {
        ThemNguoiDungFragment fragment = new ThemNguoiDungFragment();

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
        return inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_maTT = view.findViewById(R.id.ed_themNgD_tenDN);
        ed_hoTen = view.findViewById(R.id.ed_themNgD_hoTen);
        ed_matKhau = view.findViewById(R.id.ed_themNgD_MK);
        ed_nhapLaiMK = view.findViewById(R.id.ed_themNgD_nhapLaiMK);
        btn_huy = view.findViewById(R.id.btn_themNgD_huy);
        btn_them = view.findViewById(R.id.btn_themNgD_them);
        thuThuDAO = new ThuThuDAO(getActivity());
        btn_them.setOnClickListener(v -> {
            if(ed_maTT.length()==0 || ed_hoTen.length()==0 || ed_matKhau.length()==0 || ed_nhapLaiMK.length()==0){
                Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
            }else{
                if(ed_matKhau.getText().toString().equals(ed_nhapLaiMK.getText().toString())){
                    ThuThu thuThu = new ThuThu();
                    thuThu.setMaTT(ed_maTT.getText().toString());
                    thuThu.setHoTen(ed_hoTen.getText().toString());
                    thuThu.setMatKhau(ed_matKhau.getText().toString());
                    int kq = thuThuDAO.Insert(thuThu);
                    if (kq == -1) {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    if (kq == 1) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}