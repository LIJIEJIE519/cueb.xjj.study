package xjj.com.luomusic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText user;
    private EditText userName;
    private EditText pwd;
    private Button login;
    private CheckBox rememberPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setInfo();

    }

    public void initView() {
        // 获得实体类
        pref = getSharedPreferences("data", MODE_PRIVATE);
        user = (EditText) findViewById(R.id.user);
        userName = (EditText) findViewById(R.id.userName);
        pwd = (EditText) findViewById(R.id.pwd);
        rememberPwd = (CheckBox) findViewById(R.id.remember_pwd);
        login = (Button) findViewById(R.id.loginBt);
        login.setOnClickListener(LoginActivity.this);
    }
    // 设置记住密码信息
    public void setInfo(){
        // 如果没找到的默认值
        boolean isRemember = pref.getBoolean("rememberPwd", false);
        if (isRemember) {
            // 如果选中记住密码则将账号及密码设置到文本框中
            String userInfo = pref.getString("userId", "");
            String userNameInfo = pref.getString("nickName", "");
            String pwdInfo = pref.getString("pwd", "");
            user.setText(userInfo);
            userName.setText(userNameInfo);
            pwd.setText(pwdInfo);
            rememberPwd.setChecked(true);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBt:
                // 获取用户信息
                String userId = user.getText().toString();
                String nickName = userName.getText().toString();
                String pass = pwd.getText().toString();
                if (userId.length() == 0 || pass.length() == 0 || nickName.length() == 0) {
                    Toast.makeText(this,"请输入信息", Toast.LENGTH_LONG).show();
                    break;
                }
                // 调用其方法设置数据
                editor = pref.edit();
                if (rememberPwd.isChecked()){
                    // 如果选中密码，则将信息保存
                    editor.putString("userId", userId);
                    editor.putString("nickName",nickName);
                    editor.putString("pwd", pass);
                    editor.putBoolean("rememberPwd", true);
                }else {
                    editor.clear();
                }
                // 提交信息
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
