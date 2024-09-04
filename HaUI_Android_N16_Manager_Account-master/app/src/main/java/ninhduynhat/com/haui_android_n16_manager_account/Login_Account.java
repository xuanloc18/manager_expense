package ninhduynhat.com.haui_android_n16_manager_account;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executor;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.View.Quen_Mat_Khau;


public class Login_Account extends AppCompatActivity {

    //vân tay
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private ImageView image_finger_login;
    private DatabaseHelper databaseHelper;
    private TextView canhBaoDangNhap;



    private static final int REQUEST_CODE = 11111;
    private TextView txtchuyendangkys,quenMatKhau;
    private EditText edt_TenDangNhap,edt_MatKhau;
    private Button btn_DangNhapManHinh;
    public static final String LUU_TRANG_THAI_NGUOI_DUNG ="LUU_TRANG_THAI_NGUOI_DUNG";
    private String usernamedangnhap,matkhaudangnhap;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findId();


        //login to fingerPrint
        SharedPreferences sharedPreferences = getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG,MODE_PRIVATE);
        boolean islogin=sharedPreferences.getBoolean("isLogin",false);
        boolean isTurnOnFingerPrint=sharedPreferences.getBoolean("isTurnOnFingerPrint",false);
        usernamedangnhap=sharedPreferences.getString("UserName","");
        matkhaudangnhap=sharedPreferences.getString("PassWord","");
        if(islogin){
            String user= sharedPreferences.getString("UserName","");
            String pass= sharedPreferences.getString("PassWord","");
            edt_TenDangNhap.setText(user);
            edt_MatKhau.setText(pass);
        }

        if(isTurnOnFingerPrint && Check_Device_Biometric()){
            login_by_finger();
            image_finger_login.setVisibility(View.VISIBLE);
        }



        //chuyển ma hình đăng ký
        txtchuyendangkys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents= new Intent(Login_Account.this,Sign_Account.class);
                startActivity(intents);
            }
        });



        //nút đăng nhập
        btn_DangNhapManHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginState();
            }
        });
        quenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login_Account.this, Quen_Mat_Khau.class);
                startActivity(intent);
            }
        });
    }
    
    
    
    private void   findId(){

        txtchuyendangkys = findViewById(R.id.txtchuyendangkys);
        edt_TenDangNhap= findViewById(R.id.edt_TenDangNhap);
        edt_MatKhau= findViewById(R.id.edt_MatKhau);
        btn_DangNhapManHinh=findViewById(R.id.btnDangNhapManHinh);
        image_finger_login= findViewById(R.id.image_finger_login);
        canhBaoDangNhap=findViewById(R.id.canhBaoDangNhap);
        quenMatKhau=findViewById(R.id.quenMatKhau);
    }



    public void saveLoginState(){
        if(edt_TenDangNhap.getText().toString().isEmpty()||edt_MatKhau.getText().toString().isEmpty()){
            Toast.makeText(this, "Chưa điền tên đăng nhập hoặc mật kẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        databaseHelper = new DatabaseHelper(this);
        boolean check= databaseHelper.checkUserName_Password(edt_TenDangNhap.getText().toString(),edt_MatKhau.getText().toString());

        if(check){
            Intent intent = new Intent(Login_Account.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            canhBaoDangNhap.setVisibility(View.VISIBLE);
        }

    }

    //hàm check thiết bị có hỗ trợ vân tay hay không
    private boolean Check_Device_Biometric(){
        boolean check_device=false;

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                //Ứng dụng có chức năng sinh trắc học
                check_device=true;
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                //Ứng dung không có chức năng sinh trắc học
                check_device=false;
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Cảm biến không hoạt động hoặc bận", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }
        return check_device;
    }

    private void login_by_finger(){
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(Login_Account.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(Login_Account.this,MainActivity.class));
                Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Vân tay không tồn tại",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Cảm biến sinh trắc học")
                .setSubtitle("Đăng nhập bằng vân tay")
                .setNegativeButtonText("Thoát")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        image_finger_login.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor=getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG,MODE_PRIVATE).edit();
        if ( edt_TenDangNhap.getText().toString().isEmpty()||edt_MatKhau.getText().toString().isEmpty()){
            editor.putString("UserName",usernamedangnhap );
            editor.putString("PassWord",matkhaudangnhap);
            Log.e("check luu thong tin ",usernamedangnhap+"mật khẩu là: "+matkhaudangnhap);
            editor.commit();
        }else {
            editor.putString("UserName",
                    edt_TenDangNhap.getText().toString());
            editor.putString("PassWord",
                    edt_MatKhau.getText().toString());
            editor.putBoolean("Check_Device_onFinger",Check_Device_Biometric());
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG,MODE_PRIVATE);
        boolean isTurnOnFingerPrint=sharedPreferences.getBoolean("isTurnOnFingerPrint",false);
        String LUU_DU_LIEU_ANH=sharedPreferences.getString("LUU_DU_LIEU_ANH","");
        if(isTurnOnFingerPrint&&Check_Device_Biometric()){
            login_by_finger();
            image_finger_login.setVisibility(View.VISIBLE);
        }
        else {
            image_finger_login.setVisibility(View.GONE);
        }
    }

}