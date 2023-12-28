package kr.co.company.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// 회원가입 화면에서의 기능을 작성한 java 파일
public class signup extends AppCompatActivity {
    // 3개의 edittext에 대한 객체 변수
    EditText signup_id, signup_pw, signup_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);   // signup.xml을 화면으로 설정

        // xml의 뷰들을 id를 이용해 위에서 선언한 변수들과 연결
        signup_id = (EditText) findViewById(R.id.signup_id);
        signup_pw = (EditText) findViewById(R.id.signup_pw);
        signup_name = (EditText)findViewById(R.id.signup_name);
    }

    // '회원가입' 버튼이 눌리면 조건에 따라 기능 실행
    public void signup_pass(View view) {
        // 이름적은 공간이 빈칸이면 이름을 입력하라는 문구 띄움
        if (signup_name.length() == 0)
            Toast.makeText(getBaseContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
        // 아이디/비밀번호 둘 중 하나라도 4자 이상이 아니면 4자 이상 입력하라는 문구 띄움
        else if (signup_id.length() < 4 || signup_pw.length() < 4)
            Toast.makeText(getBaseContext(), "4자 이상 입력하세요.", Toast.LENGTH_SHORT).show();
        else {
            // 조건이 다 만족하면 명시적 인텐트를 사용해 login 액티비티로 넘어감
            Intent intent = new Intent(signup.this, login.class);
            // 인텐트에 아이디(키:id)와 비밀번호(키:pw)에 대한 정보를 저장
            intent.putExtra("id", signup_id.getText().toString());
            intent.putExtra("pw", signup_pw.getText().toString());
            startActivity(intent);
            // 가입 완료 문자 띄움
            Toast.makeText(getBaseContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            // 현재 실행중이던 회원가입 액티비티 종료
            finish();
        }
    }
}




