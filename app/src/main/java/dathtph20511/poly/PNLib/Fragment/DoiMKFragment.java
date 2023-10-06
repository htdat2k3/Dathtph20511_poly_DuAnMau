package dathtph20511.poly.PNLib.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import dathtph20511.poly.PNLib.DataBase.ThuThuDAO;
import dathtph20511.poly.PNLib.Model.ThuThu;
import dathtph20511.poly.PNLib.R;


public class DoiMKFragment extends Fragment {

    private TextInputEditText ed_MKcu,ed_MKmoi,ed_nhapLaiMK;
    private ThuThuDAO thuThuDAO;
    public DoiMKFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DoiMKFragment newInstance() {
        DoiMKFragment fragment = new DoiMKFragment();

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
        return inflater.inflate(R.layout.fragment_doi_m_k, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_MKcu = view.findViewById(R.id.ed_doiMK_MKcu);
        ed_MKmoi = view.findViewById(R.id.ed_doiMK_MKmoi);
        ed_nhapLaiMK = view.findViewById(R.id.ed_doiMK_nhapLaiMK);
        thuThuDAO = new ThuThuDAO(getActivity());
        String maTT = getArguments().getString("TK");
        view.findViewById(R.id.btn_doiMK_doi).setOnClickListener(v -> {
            ThuThu thuThu = thuThuDAO.getId(maTT);
            if(ed_MKcu.getText().toString().equals(thuThu.getMatKhau()) && ed_MKmoi.getText().toString().equals(ed_nhapLaiMK.getText().toString())){
                thuThu.setMatKhau(ed_MKmoi.getText().toString());
                thuThuDAO.update(thuThu);
                Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.btn_doiMK_huy).setOnClickListener(v -> {

        });
    }
}