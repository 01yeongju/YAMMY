package kr.co.company.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

//  결제 완료 화면에서의 기능을 작성한 java 파일
public class finish extends AppCompatActivity {
    // 대기번호를 1~200사이의 값으로 랜덤 생성
    String random_number = Integer.toString((int)(Math.random()*200)+1);

    // 각 액티비티에 대한 객체 생성
    general_menu gm;
    payment pm;
    select_case sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish);   // finish.xml을 액티비티 화면으로 설정

        // 기존 액티비티를 객체와 연결
        gm = (general_menu) general_menu.general_menu_act;
        pm = (payment) payment.payment_act;
        sc = (select_case) select_case.select_case_act;

        // 위에서 생성해 둔 객체와 id를 통하여 연결
        TextView number = (TextView) findViewById(R.id.number);

        // 대기번호를 화면에 띄움
        number.setText(random_number);
    }

    // '메인으로 돌아가기' 버튼 클릭 시 select_case 액티비티로 전환
    public void back_main(View view) {
        // 명시적 인텐트 사용
        Intent intent = new Intent(finish.this, select_case.class);
        // 대기번호(키:대기번호)를 인텐트에 저장하여 전달
        intent.putExtra("대기번호", random_number);
        startActivity(intent);

        // 열려있던 액티비티들 종료
        finish();
        pm.finish();
        gm.finish();
        sc.finish();
    }
}
