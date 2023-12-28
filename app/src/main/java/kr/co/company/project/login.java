package kr.co.company.project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;

// 로그인 화면에서의 기능을 작성한 java 파일
public class login extends AppCompatActivity {
    EditText input_id;   // 아이디 입력을 위한 뷰의 객체 변수
    EditText input_pw;   // 비밀번호 입력을 위한 뷰의 객체 변수

    // 회원가입한 아이디, 비밀번호 정보를 저장할 ArrayList 변수
    ArrayList<String> id_data = new ArrayList<>();
    ArrayList<String> pw_data = new ArrayList<>();

    // 비트맵을 사용하기 위한 클래스 생성
    public class MyView extends View {
        public MyView(login context) { super(context); }

        // onDraw 메소드 활용
        // 로고 이미지를 리소스로 표시하지 않고 비트맵을 이용해 직접 읽어옴
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // 로고 이미지의 리소스 식별자를 이용해 리소스를 찾고 디코딩하여 비트맵으로 반환
            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            // 비트맵을 얻은 후 Canvas 클래스의 drawBitmap() 메소드로 화면에 그림
            canvas.drawBitmap(logo, 0, 0, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);   // login.xml을 화면으로 설정

        // xml의 뷰들을 id를 이용해 위에서 선언한 변수들과 연결
        input_id = (EditText) findViewById(R.id.input_id);
        input_pw = (EditText) findViewById(R.id.input_pw);

        // xml 파일의 id:logo를 가진 리니어 레이아웃에 비트맵으로 읽어온 이미지를 추가
        LinearLayout logo_image = (LinearLayout) findViewById(R.id.logo);
        MyView displayView = new MyView(this);
        logo_image.addView(displayView);

        // signup 액티비티에서 인텐트로 넘어온 정보를 id_data, pw_data(ArrayList)에 저장
        Intent intent = getIntent();
        id_data.add(intent.getStringExtra("id"));
        pw_data.add(intent.getStringExtra("pw"));
    }

    // '로그인' 버튼이 눌리면 명시적 인텐트를 사용해 select_case 액티비티로 넘어감
    public void login_button(View view) {

        // 아이디 또는 비밀번호 입력칸 중 하나라도 입력이 되지 않은 경우 아이디/비밀번호를 입력하라는 문구 출력
        if (input_id.length() == 0 || input_pw.length() == 0)
            Toast.makeText(getBaseContext(), "아이디/비밀번호를 입력하세요.",
                    Toast.LENGTH_SHORT).show();
        // 둘 다 입력된 경우
        else {
            // 아이디/비밀번호 중 하나라도 일치하지 않으면 일치하지 않는다는 문구 출력
            if (id_data.contains(input_id.getText().toString()) == false ||
                    pw_data.contains(input_pw.getText().toString()) == false)
                Toast.makeText(getBaseContext(), "아이디/비밀번호가 일치하지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            else {
                // 둘다 일치하면 select_case 액티비티 화면으로 넘어감
                Intent intent = new Intent(login.this, select_case.class);
                startActivity(intent);
                // 로그인 되었다는 문구 출력
                Toast.makeText(getBaseContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // '회원가입' 버튼이 눌리면 명시적 인텐트를 사용해 signup 액티비티로 넘어감
    public void signup_button(View view) {
        Intent intent = new Intent(login.this, signup.class);
        startActivity(intent);
    }
}





