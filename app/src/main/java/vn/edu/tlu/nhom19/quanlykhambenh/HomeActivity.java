package vn.edu.tlu.nhom19.quanlykhambenh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private TextView textViewWelcomeHome;
    private LinearLayout btnHoiDap, btnLichHen, btnTrangChu, btnThongBao, btnTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewWelcomeHome = findViewById(R.id.textViewWelcomeHome);
        btnHoiDap = findViewById(R.id.btn_hoi_dap);
        btnLichHen = findViewById(R.id.btn_lich_hen);
        btnTrangChu = findViewById(R.id.btn_trang_chu);
        btnThongBao = findViewById(R.id.btn_thong_bao);
        btnTaiKhoan = findViewById(R.id.btn_tai_khoan);

        // Lấy dữ liệu tên người dùng và vai trò từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String userName = intent.getStringExtra("user_name");
            String userRole = intent.getStringExtra("user_role");
            if (userName != null && !userName.isEmpty()) {
                textViewWelcomeHome.setText("Chào mừng, " + userName + " (" + userRole + ")!");
                textViewWelcomeHome.setVisibility(View.VISIBLE);
            }
        }

        // Xử lý sự kiện click cho các nút ở Bottom Navigation Bar
        btnHoiDap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Bạn đã click Hỏi đáp 24/7", Toast.LENGTH_SHORT).show();
                // Thêm Intent để chuyển sang Activity tương ứng nếu có
            }
        });

        btnLichHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Bạn đã click Lịch hẹn", Toast.LENGTH_SHORT).show();
                // Thêm Intent để chuyển sang Activity tương ứng nếu có
            }
        });

        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Bạn đang ở Trang chủ", Toast.LENGTH_SHORT).show();
                // Không làm gì vì đã ở HomeActivity
            }
        });

        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Bạn đã click Thông báo", Toast.LENGTH_SHORT).show();
                // Thêm Intent để chuyển sang Activity tương ứng nếu có
            }
        });

        btnTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Bạn đã click Tài khoản", Toast.LENGTH_SHORT).show();
                // Thêm Intent để chuyển sang Activity tương ứng nếu có
            }
        });
    }
}
