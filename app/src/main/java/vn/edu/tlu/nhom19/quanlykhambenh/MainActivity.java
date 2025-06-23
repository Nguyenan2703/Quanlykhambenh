package vn.edu.tlu.nhom19.quanlykhambenh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp, textViewForgetPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ các View
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        textViewForgetPassword = findViewById(R.id.textViewForgetPassword);

        // Xử lý sự kiện click cho nút Login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra đăng nhập
                    User user = databaseHelper.checkUser(email, password);
                    if (user != null) {
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công! Vai trò: " + user.getRole(), Toast.LENGTH_SHORT).show();
                        // Chuyển sang HomeActivity
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("user_name", user.getName()); // Truyền tên người dùng sang HomeActivity
                        intent.putExtra("user_role", user.getRole()); // Truyền vai trò người dùng
                        startActivity(intent);
                        finish(); // Đóng LoginActivity
                    } else {
                        Toast.makeText(MainActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Xử lý sự kiện click cho TextView "Sign Up"
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang RegisterActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện click cho TextView "Forget password" (có thể thêm logic sau)
        textViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chức năng quên mật khẩu đang được phát triển", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
