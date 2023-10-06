package dathtph20511.poly.PNLib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import dathtph20511.poly.PNLib.DataBase.ThuThuDAO;
import dathtph20511.poly.PNLib.Fragment.DoanhThuFragment;
import dathtph20511.poly.PNLib.Fragment.DoiMKFragment;
import dathtph20511.poly.PNLib.Fragment.LoaiSachFragment;
import dathtph20511.poly.PNLib.Fragment.PhieuMuonFragment;
import dathtph20511.poly.PNLib.Fragment.SachFragment;
import dathtph20511.poly.PNLib.Fragment.ThanhVienFragment;
import dathtph20511.poly.PNLib.Fragment.ThemNguoiDungFragment;
import dathtph20511.poly.PNLib.Fragment.Top10Fragment;
import dathtph20511.poly.PNLib.Model.ThuThu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    private ThuThuDAO thuThuDAO;
    private TextView tv_header;
    private DoiMKFragment doiMkFragment = new DoiMKFragment();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        frameLayout = findViewById(R.id.frameLayout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();

        Intent intent = getIntent();
        String maTT = intent.getStringExtra("TK");
        Bundle bundle = new Bundle();
        bundle.putString("TK", maTT);
        doiMkFragment.setArguments(bundle);

        thuThuDAO = new ThuThuDAO(this);
        ThuThu thuThu = thuThuDAO.getId(maTT);
        MenuItem item = navigationView.getMenu().findItem(R.id.ngd_themNGD);
        if (thuThu.getMaTT().equals("admin")) {
            item.setEnabled(true);
        } else {
            item.setEnabled(false);
        }
        View header = navigationView.getHeaderView(0);
        tv_header = header.findViewById(R.id.tv_head);
        tv_header.setText(thuThu.getHoTen());
        replaceFragment(new PhieuMuonFragment());
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            super.onBackPressed();
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) { int id = item.getItemId();
        if (id == R.id.ql_phieuMuon) {
            //hiển thị màn hình bài 1
            toolbar.setTitle("Quản lý phiếu mượn");
            replaceFragment(new PhieuMuonFragment());
            //  replaceFragment();
        } else if (id == R.id.ql_loaiSach) {
            // hiển thị màn hình bài 2
            toolbar.setTitle("Quản lý loại sách");
            replaceFragment(new LoaiSachFragment());
            // replaceFragment();
        } else if (id == R.id.ql_sach) {
            toolbar.setTitle("Quản lý sách");
            replaceFragment(new SachFragment());
            // replaceFragment();
        } else if (id == R.id.ql_thanhVien) {
            toolbar.setTitle("Quản lý thành viên");
            replaceFragment(new ThanhVienFragment());
        } else if (id == R.id.tk_top10) {
            toolbar.setTitle("10 Quyển sách mượn nhiều nhất");
            replaceFragment(new Top10Fragment());
            // replaceFragment();
        } else if (id == R.id.tk_doanhThu) {
            toolbar.setTitle("Doanh thu");
            replaceFragment(new DoanhThuFragment());
            // replaceFragment();
        } else if (id == R.id.ngd_themNGD) {
            toolbar.setTitle("Thêm người dùng");
            // replaceFragment();
            replaceFragment(new ThemNguoiDungFragment());
        } else if (id == R.id.ngd_doiMK) {
            toolbar.setTitle("Đổi mật khẩu");
            replaceFragment(doiMkFragment);
            // replaceFragment();
        } else if (id == R.id.ngd_dangXuat) {
            finish();
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }
}
