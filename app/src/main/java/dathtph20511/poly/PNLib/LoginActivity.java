package dathtph20511.poly.PNLib;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import dathtph20511.poly.PNLib.DataBase.ThuThuDAO;
import dathtph20511.poly.PNLib.Fragment.DoiMKFragment;
import dathtph20511.poly.PNLib.Model.ThuThu;

public class LoginActivity extends AppCompatActivity   {

    private ThuThuDAO thuThuDAO;
    private TextInputEditText ed_Username, ed_Password;
    private Button btnLogin, btnCancel;
    private CheckBox chk_luu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_Username = findViewById(R.id.ed_login_TK);
        ed_Password = findViewById(R.id.ed_login_MK);
        chk_luu = findViewById(R.id.chk_luuMK);
        btnLogin = findViewById(R.id.btn_login_dangNhap);
        btnCancel = findViewById(R.id.btn_login_huy);
        thuThuDAO = new ThuThuDAO(LoginActivity.this);
        DoiMKFragment doiMkFragment = new DoiMKFragment();
        List<ThuThu> arrayList = thuThuDAO.getAllThuThu();
        List<String> list = new ArrayList<>();
        list = readPreference();
        if (list.size() > 0) {
            ed_Username.setText(list.get(0));
            ed_Password.setText(list.get(1));
            chk_luu.setChecked(Boolean.parseBoolean(list.get(2)));
        }
        btnCancel.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });

        btnLogin.setOnClickListener(v -> {
            String tk = ed_Username.getText().toString();
            String mk = ed_Password.getText().toString();
            boolean status = chk_luu.isChecked();
            for (int i = 0; i < thuThuDAO.getAllThuThu().size(); i++) {
                ThuThu thuThu = arrayList.get(i);
                Log.e("user", thuThu.getMaTT() + " " + thuThu.getMatKhau());
                if (tk.equals(thuThu.getMaTT()) && mk.equals(thuThu.getMatKhau())) {

                    savePreference(tk, mk, status);
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("TK", tk);
                    startActivity(intent);
                    return;
                }
            }

            if (tk.length() == 0 || mk.length() == 0) {
                Toast.makeText(this, "Không để trống", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sai thông tin", Toast.LENGTH_SHORT).show();
            }

        });
    }



    public void savePreference(String tk, String mk, Boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status) { // Khong luu
            editor.clear();
        } else { // luu
            editor.putString("TK", tk);
            editor.putString("MK", mk);
            editor.putBoolean("CHK", status);
        }
        editor.commit();
    }

    public List<String> readPreference() {
        List<String> list = new ArrayList<>();
        SharedPreferences s = getSharedPreferences("MY_FILE", MODE_PRIVATE);
        list.add(s.getString("TK", ""));
        list.add(s.getString("MK", ""));
        list.add(String.valueOf(s.getBoolean("CHK", false)));
        return list;
    }
}
