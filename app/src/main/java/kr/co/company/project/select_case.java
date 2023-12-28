package kr.co.company.project;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//  메인 화면에서의 기능을 작성한 java 파일
public class select_case extends AppCompatActivity {
    // 변수 선언
    TextView toss_number;
    Intent intent;

    // select_case 액티비티에 전역변수 선언
    // 결제완료창에서 메인화면으로 돌아갈 때 실행되고있는 이 액티비티를 끝내기 위함
    public static Activity select_case_act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_case);   // select_case.xml을 액티비티 화면으로 설정
        select_case_act = select_case.this;  // 액티비티의 정보 저장

        // 결제 완료 후 메인화면으로 넘어왔을 때 대기번호를 띄우기 위한 코드
        toss_number = (TextView)findViewById(R.id.toss_number);
        intent = getIntent();   // 인텐트에서 정보를 받아옴
        toss_number.setText(intent.getStringExtra("대기번호"));   // 텍스트뷰의 text 속성으로 지정
    }

    // '주문하기' 버튼이 눌리면 명시적 인텐트를 사용해 general_menu 액티비티로 넘어감
    public void general_menu(View view) {
        Intent intent = new Intent(select_case.this, general_menu.class);
        startActivity(intent);
    }

    // '백반 메뉴보기' 버튼이 눌리면 암시적 인텐트를 사용해 학교 학식정보 웹사이트로 넘어감
    public void week_menu(View view) {
        // 웹사이트의 정보를 사용자에게 표시
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse
                ("https://www.hanbat.ac.kr/prog/carteGuidance/kor/sub06_030301/C1/calendar.do"));
        startActivity(intent);
    }

    // '리뷰 남기기' 버튼이 눌리면 명시적 인텐트를 사용해 review_menu 액티비티로 넘어감
    public void review_menu(View view) {
        Intent intent = new Intent(select_case.this, review_menu.class);
        startActivity(intent);
    }

    // 옵션의 로그아웃 기능에 관한 코드
    // onCreateOptionsMenu() 메소드 오버라이딩
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflater를 통하여 logout_item.xml에 정의된 내용을 menu 객체에 추가
        getMenuInflater().inflate(R.menu.logout_item, menu);
        return true;
    }

    // 옵션에서 로그아웃을 누르면 호출되는 메소드
    // 로그아웃이 눌리면 select_case 액티비티로 전환
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(select_case.this, login.class);
            startActivity(intent);
            // 작동되고 있던 모든 액티비티를 종료
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}



