package vn.edu.tlu.nhom19.quanlykhambenh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuanLyKhamBenh.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_GENDER = "gender";
    public static final String COL_PHONE = "phone";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ROLE = "role";

    // Lệnh tạo bảng
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NAME + " TEXT,"
            + COL_EMAIL + " TEXT UNIQUE," // Email là duy nhất
            + COL_GENDER + " TEXT,"
            + COL_PHONE + " TEXT,"
            + COL_PASSWORD + " TEXT,"
            + COL_ROLE + " TEXT DEFAULT 'user'" // Mặc định là 'user'
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng khi cơ sở dữ liệu được tạo lần đầu
        db.execSQL(CREATE_TABLE_USERS);
        // Chèn dữ liệu mẫu
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ và tạo lại khi nâng cấp phiên bản cơ sở dữ liệu
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Phương thức chèn dữ liệu mẫu
    private void insertSampleData(SQLiteDatabase db) {
        addUser(db, new User(0, "Admin", "admin@mail.com", "", "19001515", "111111", "admin"));
        addUser(db, new User(1, "Lê Trần Hiếu", "Lehieu@mail.com", "Nam", "372250073", "123456", "user"));
        addUser(db, new User(2, "Nguyễn Thị Mỹ Linh", "Mylinh@mail.com", "Nữ", "874633278", "654321", "user"));
        addUser(db, new User(3, "Bùi Linh Nga", "Linhnga@mail.com", "Nữ", "922467797", "112233", "user"));
        addUser(db, new User(4, "Nguyễn Minh Hiển", "Minhhien@mail.com", "Nam", "904655173", "445566", "user"));
        addUser(db, new User(5, "Nguyễn Bích Ngọc", "Bngoc@mail.com", "Nữ", "328116749", "123123", "user"));
    }

    // Thêm người dùng mới vào cơ sở dữ liệu (được gọi từ onCreate và RegisterActivity)
    public boolean addUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        // Không cần user_id nếu là AUTOINCREMENT, nhưng để chèn dữ liệu mẫu, ta có thể truyền vào 0
        // và SQLite sẽ tự động tăng nếu nó là khóa chính AUTOINCREMENT
        if (user.getUserId() != 0) {
            values.put(COL_USER_ID, user.getUserId());
        }
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_GENDER, user.getGender());
        values.put(COL_PHONE, user.getPhone());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_ROLE, user.getRole());

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1; // Trả về true nếu chèn thành công
    }

    // Thêm người dùng mới vào cơ sở dữ liệu (chỉ dùng cho RegisterActivity)
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_GENDER, user.getGender());
        values.put(COL_PHONE, user.getPhone());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_ROLE, user.getRole());

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }


    // Kiểm tra đăng nhập
    public User checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        User user = null;
        try {
            String[] columns = {COL_USER_ID, COL_NAME, COL_EMAIL, COL_GENDER, COL_PHONE, COL_PASSWORD, COL_ROLE};
            String selection = COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?";
            String[] selectionArgs = {email, password};

            cursor = db.query(TABLE_USERS, // Tên bảng
                    columns,                  // Các cột cần trả về
                    selection,                // Các điều kiện WHERE
                    selectionArgs,            // Giá trị cho điều kiện WHERE
                    null,                     // Nhóm hàng
                    null,                     // Lọc nhóm hàng
                    null);                    // Thứ tự sắp xếp

            if (cursor != null && cursor.moveToFirst()) {
                // Tìm thấy người dùng, tạo đối tượng User
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
                String userPassword = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD));
                String role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE));
                user = new User(userId, name, userEmail, gender, phone, userPassword, role);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return user;
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] columns = {COL_EMAIL};
            String selection = COL_EMAIL + " = ?";
            String[] selectionArgs = {email};

            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
}
