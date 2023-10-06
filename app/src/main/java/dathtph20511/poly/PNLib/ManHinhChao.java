package dathtph20511.poly.PNLib;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ManHinhChao extends AppCompatActivity {

//    Handler handler;

    TextInputEditText editText;
    Button btnMHC;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        editText= findViewById(R.id.ed_MHC);
        btnMHC = findViewById(R.id.btnManHinhChao);

        btnMHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkmaSV();

            }
        });

    }
    public void checkmaSV(){
        String msv = editText.getText().toString();
        if (msv.length() > 0 && msv == "ph20511") {
            Toast.makeText(ManHinhChao.this, "Sai ma sinh vien", Toast.LENGTH_SHORT).show();
        } else {
            if (msv.length() == 0 || msv.length() == 0) {
                Toast.makeText(ManHinhChao.this, "Khong duoc de trong", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent(ManHinhChao.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
        }
    }

//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(ManHinhChao.this,LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        },3000);


}