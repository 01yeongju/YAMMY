package kr.co.company.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// 결제 목록 액티비티에 대한 기능을 나타내는 java 파일
public class payment extends AppCompatActivity {
    ListView payment_food_list;
    int sum = 0; // 총 결제 금액 저장

    // payment 액티비티에 전역변수 선언
    // 결제완료창에서 메인화면으로 돌아갈 때 실행되고있는 이 액티비티를 끝내기 위함
    public static Activity payment_act;
    Intent intent;

    // 인텐트로 받아온 정보를 저장할 변수
    String[] payment_food_name;
    String[] payment_food_number;
    String[] payment_food_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);    // payment.xml을 액티비티 화면으로 설정
        payment_act = payment.this;  // 액티비티의 정보 저장

        // 인텐트에서 받아온 정보를 나타내기 위한 코드
        intent = getIntent();
        payment_food_name = intent.getStringArrayExtra("음식이름");
        payment_food_number = intent.getStringArrayExtra("주문개수");
        payment_food_price = intent.getStringArrayExtra("주문가격");

        // id로 xml과 객체를 연결
        TextView payment_all_price = (TextView)findViewById(R.id.payment_all_price);
        payment_food_list = (ListView) findViewById(R.id.payment_food_list);

        // 총 결제 금액을 나타내는 코드 ( 제일 처음에 뜨는 총 결제값 : 리스트에 있는 값을 삭제하면 이건 사라짐)
        for (int i = 0; i < payment_food_price.length; i++) {
            sum += Integer.parseInt(payment_food_price[i]);
        }
        payment_all_price.setText(Integer.toString(sum)+"원");

        // 어댑터 설졍
        CustomList3 adapter3 = new CustomList3(payment.this);
        payment_food_list.setAdapter(adapter3);   // 리스트뷰에 어댑터 설정
    }

    // 더 담으러가기 버튼 클릭시 현재 실행중인 액티비티 종료
    public void add_food(View view) {
        onBackPressed();
    }

    // 결제하기 버튼 클릭시 결제 완료 액티비티로 전환
    public void payment(View view) {
        Intent intent = new Intent(payment.this, finish.class);
        startActivity(intent);
    }

    // 결제 메뉴에 대한 ArrayAdapter를 상속받는 어댑터를 내부클래스로 정의
    public class CustomList3 extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList3(Activity context) {    // 커스텀리스트 생성자 구현
            // 사용할 아이템 레이아웃 정의
            super(context, R.layout.payment_listview_item, payment_food_name);
            this.context = context;
        }

        // getView() : 어댑터에 추가된 리소스를 표시하는 view 객체 반환
        //             메소드가 반환하는 뷰를 사용하여 항목 표시
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // payment_listview_item.xml을 inflater로써 view 객체로 생성
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.payment_listview_item, null, true);

            // 화면에 표시된 View(layout이 inflate된)으로부터 위젯에 대한 참조
            TextView pay_food_name = (TextView) rowView.findViewById(R.id.payment_food_name);
            TextView pay_food_number = (TextView) rowView.findViewById(R.id.payment_food_num2);
            TextView pay_food_price = (TextView) rowView.findViewById(R.id.payment_food_price2);

            // 초반에 저장한 데이터 배열을 활용하여 항목들을 설정
            pay_food_name.setText(payment_food_name[position]);
            pay_food_number.setText(payment_food_number[position]+"개");
            pay_food_price.setText(payment_food_price[position]+"원");

            return rowView;
        }
    }

    // 옵션에서 로그아웃을 누르면 로그인 화면으로 이동 : 코드분석은 general_menu에 작성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(payment.this, login.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}
