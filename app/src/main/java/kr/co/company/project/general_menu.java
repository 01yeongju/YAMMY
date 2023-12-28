package kr.co.company.project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//  메뉴 주문 화면에서의 기능을 작성한 java 파일
public class general_menu extends AppCompatActivity {
    // 변수 선언
    ListView kor_food_list, wes_food_list;   // 한식, 양식 리스트뷰 변수
    int cnt = 1, price = 0;   // cnt = 주문 개수, price = 개수에 따른 가격

    // general_menu 액티비티에 전역변수 선언
    // 결제완료창에서 메인화면으로 돌아갈 때 실행되고있는 이 액티비티를 끝내기 위함
    public static Activity general_menu_act;

    // 한식에 관한 데이터 저장
    Integer[] kfood_image = {
            R.drawable.food1,R.drawable.food2, R.drawable.food3,
            R.drawable.food4, R.drawable.food5, R.drawable.food6
    };
    String[] kfood_name = {
            "순두부찌개", "촌돼지김치찌개", "부대찌개",
            "고추장뚝배기불고기", "닭갈비덮밥", "제육덮밥"
    };
    String[] kfood_price = {
            "6000", "6000", "6000",
            "6000", "5500", "5500"};

    // 양삭에 관한 데이터 저장
    Integer[] wfood_image = {
            R.drawable.food7, R.drawable.food8, R.drawable.food9, R.drawable.food10,
            R.drawable.food11, R.drawable.food12, R.drawable.food13
    };
    String[] wfood_name = {
            "직생돈+알밥", "직생돈+우동", "치즈돈까스+알밥", "치즈돈까스+우동",
            "치킨마요덮밥", "해장라면+공깃밥", "떡만두라면+공깃밥"};
    String[] wfood_price = {
            "6000", "6000", "6500", "6500",
            "5500", "4500", "4500"
    };

    // 인텐트로 넘겨야 하는 모든 정보를 저장한 변수
    ArrayList<String>pass_food_name = new ArrayList<>();   // 사용자가 추가한 음식이름 정보
    ArrayList<String>pass_food_number = new ArrayList<>();   // 사용자가 추가한 음식의 개수
    ArrayList<String>pass_food_price = new ArrayList<>();   // 사용자가 추가한 음식 개수에 따른 가격

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_menu);   // general_menu.xml을 액티비티 화면으로 설정
        general_menu_act = general_menu.this;  // 액티비티의 정보 저장

        // 한식/양식 버튼을 사용하기 위해 위에서 생성해 둔 객체와 id를 통하여 연결
        kor_food_list = (ListView) findViewById(R.id.kor_food_list);
        wes_food_list = (ListView) findViewById(R.id.wes_food_list);
    }

    // 한식 또는 양식 버튼 클릭시 리스트뷰 활성화
    // 리스트뷰를 프레임레이아웃을 이용해 보여줌
    public void food_button(View view) {
        // 처음에는 둘 다 보이지 않는 상태로 지정
        kor_food_list.setVisibility(view.INVISIBLE);
        wes_food_list.setVisibility(view.INVISIBLE);

        // 메뉴 선택시, 커스텀 대화상자를 띄우기 위한 기본 설정
        Dialog pay_dialog = new Dialog(general_menu.this);
        // 커스텀 대화 상자의 화면이 custom_dialog.xml에 저장되어 있음
        pay_dialog.setContentView(R.layout.custom_dialog);
        pay_dialog.setTitle("추가 화면");

        // 각 뷰에 대한 객체 생성 후 id를 통해 연결
        Button increase = (Button)pay_dialog.findViewById(R.id.increase);   // 증가 버튼
        Button decrease = (Button)pay_dialog.findViewById(R.id.decrease);   // 감소 버튼
        Button cancel = (Button)pay_dialog.findViewById(R.id.cancel);    // 취소 버튼
        Button pay = (Button)pay_dialog.findViewById(R.id.pay);     // 추가버튼
        TextView count = (TextView) pay_dialog.findViewById(R.id.count);    // 메뉴 선택 개수를 나타내는
                                                                            // 텍스트뷰
        TextView all_price = (TextView) pay_dialog.findViewById(R.id.all_price);   // 가격을 나타내는
                                                                                   // 텍스트뷰

        // 한식 / 양식 버튼 각각에 대한 이벤트 처리
        switch (view.getId()) {
            // 한식 버튼이 클릭된 경우
            case R.id.kor_food:
                // 어댑터 생성
                CustomList1 adapter1 = new CustomList1(general_menu.this);
                kor_food_list.setAdapter(adapter1);   // 한식 리스트뷰에 어댑터 설정
                kor_food_list.setVisibility(view.VISIBLE);   // 한식에 대한 리스트뷰가 보이도록 설정

                // 한식에서 리스트뷰 클릭 이벤트 처리
                kor_food_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    // 리스트뷰를 선택하면 커스텀 대화상자 발생
                    // 커스텀 대화상자에 있는 버튼을 클릭하는 경우 발생하는 것에 대한 코드
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        cnt = 1; price = 0;

                        // 처음 뜨는 가격을 셋팅
                        // 개수 = 1, 가격 = 선택된 음식 1개의 가격
                        all_price.setText(kfood_price[position]+"원");
                        count.setText("1");

                        // 취소버튼 클릭 : 익명클래스로 처리
                        cancel.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                // 취소되었다는 문구 출력
                                Toast.makeText(getApplicationContext(), "취소하였습니다.",
                                        Toast.LENGTH_SHORT).show();
                                pay_dialog.dismiss();   // 커스텀 대화상자 사라짐
                            }
                        });

                        // 추가버튼 클릭
                        pay.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {

                                // 인텐트를 이용하여 넘길 정보를 저장
                                pass_food_name.add(kfood_name[position]);
                                // String 형식으로 받아와서 저장
                                pass_food_number.add(count.getText().toString());
                                pass_food_price.add(all_price.getText().toString().replace
                                        ("원", ""));  // '원'을 제거한 후 저장

                                // 추가되었다는 문구 출력
                                Toast.makeText(getApplicationContext(), "추가되었습니다.",
                                        Toast.LENGTH_SHORT).show();
                                pay_dialog.dismiss();  // 커스텀 대화상자 사라짐
                            }
                        });

                        // 증가버튼 클릭
                        increase.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                cnt++;
                                count.setText(cnt+"");    // 화면에 표시되는 값 +1
                                // 곱셈을 위해 String 형식을 int로 바꿈
                                price = Integer.parseInt(kfood_price[position])*cnt;
                                // int형을 다시 String으로 바꾸어 셋팅
                                all_price.setText(Integer.toString(price)+"원");
                            }
                        });

                        // 감소버튼 클릭
                        decrease.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                cnt --;
                                count.setText(cnt+"");    // 화면에 표시되는 값 -1
                                // 증가 버튼과 동일
                                price = Integer.parseInt(kfood_price[position])*cnt;
                                all_price.setText(Integer.toString(price)+"원");
                            }
                        });
                        pay_dialog.show();    // 커스텀 대화상자 실행
                    }
                });
                break;

            // 양식 버튼이 클릭된 경우
            case R.id.wes_food:
                // 어댑터 생성
                CustomList2 adapter2 = new CustomList2(general_menu.this);
                wes_food_list.setAdapter(adapter2);   // 양식 리스트뷰에 어댑터 설정
                wes_food_list.setVisibility(view.VISIBLE);   // 양식에 대한 리스트뷰가 보이도록 설정

                // 양식에서의 리스트뷰 클릭 이벤트 처리 : 한식에서의 경우와 코드 동일
                wes_food_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    // 리스트뷰를 선택하면 커스텀 대화상자 발생.
                    // 커스텀 대화상자에 있는 버튼을 클릭하는 경우 발생하는 것에 대한 코드
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        cnt = 1; price = 0;

                        // 처음 뜨는 가격
                        all_price.setText(wfood_price[position]+"원");
                        count.setText("1");

                        // 취소버튼 클릭
                        cancel.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "취소하였습니다.",
                                        Toast.LENGTH_SHORT).show();
                                pay_dialog.dismiss();
                            }
                        });

                        // 추가버튼 클릭
                        pay.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {

                                // 인텐트를 이용하여 넘길 정보를 저장
                                pass_food_name.add(wfood_name[position]);
                                pass_food_number.add(count.getText().toString());
                                pass_food_price.add(all_price.getText().toString().replace
                                        ("원", ""));

                                Toast.makeText(getApplicationContext(), "추가되었습니다.",
                                        Toast.LENGTH_SHORT).show();
                                pay_dialog.dismiss();
                            }
                        });

                        // 증가버튼 클릭
                        increase.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                cnt++;
                                count.setText(cnt+"");
                                price = Integer.parseInt(wfood_price[position])*cnt;
                                all_price.setText(Integer.toString(price)+"원");
                            }
                        });

                        // 감소버튼 클릭
                        decrease.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                cnt --;
                                count.setText(cnt+"");
                                price = Integer.parseInt(wfood_price[position])*cnt;
                                all_price.setText(Integer.toString(price)+"원");
                            }
                        });
                        pay_dialog.show();    // 커스텀 대화상자 실행
                    }
                });
                break;
        }
    }

    // 한식에 대한 ArrayAdapter를 상속받는 어댑터를 내부클래스로 정의
    public class CustomList1 extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList1(Activity context) {    // 커스텀리스트 생성자 구현
            super(context, R.layout.food_listview_item, kfood_name);   // 사용할 아이템 레이아웃 정의
            this.context = context;
        }

        // getView() : 어댑터에 추가된 리소스를 표시하는 view 객체 반환
        //             메소드가 반환하는 뷰를 사용하여 항목 표시
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // food_listview_item.xml을 inflater로써 view 객체로 생성
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.food_listview_item, null, true);

            // 이미지, 텍스트 데이터를 코드와 연결
            ImageView food_image = (ImageView) rowView.findViewById(R.id.food_image);
            TextView food_name = (TextView) rowView.findViewById(R.id.food_name);
            TextView food_price = (TextView) rowView.findViewById(R.id.food_price);

            // 초반에 저장한 데이터 배열을 활용하여 항목들을 설정
            food_image.setImageResource(kfood_image[position]);
            food_name.setText(kfood_name[position]);
            food_price.setText(kfood_price[position]+"원");

            return rowView;
        }
    }

    // 양식에 대한 ArrayAdapter를 상속받는 어댑터를 내부클래스로 정의
    public class CustomList2 extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList2(Activity context) {    // 커스텀리스트 생성자 구현
            super(context, R.layout.food_listview_item, wfood_name);   // 사용할 아이템 레이아웃 정의
            this.context = context;
        }

        // getView() : 어댑터에 추가된 리소스를 표시하는 view 객체 반환
        //             메소드가 반환하는 뷰를 사용하여 항목 표시
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // food_listview_item.xml을 inflater로써 view 객체로 생성
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.food_listview_item, null, true);

            // 이미지, 텍스트의 데이터를 코드와 연결
            ImageView food_image = (ImageView) rowView.findViewById(R.id.food_image);
            TextView food_name = (TextView) rowView.findViewById(R.id.food_name);
            TextView food_price = (TextView) rowView.findViewById(R.id.food_price);

            // 초반에 저장한 데이터 배열을 활용하여 항목들을 설정
            food_image.setImageResource(wfood_image[position]);
            food_name.setText(wfood_name[position]);
            food_price.setText(wfood_price[position]);

            return rowView;
        }
    }

    // '결제 목록' 버튼이 눌리면 명시적 인텐트를 사용해 payment 액티비티로 넘어감
    public void payment_list(View view) {

        // 인텐트로 보낼 정보들이 ArrayList<String> 형식으로 되어 있으므로 그것을 String[] 형식으로 바꾸어줌
        String[] p_food_name = pass_food_name.toArray(new String[pass_food_name.size()]);
        String[] p_food_number = pass_food_number.toArray(new String[pass_food_number.size()]);
        String[] p_food_price = pass_food_price.toArray(new String[pass_food_price.size()]);

        // 인텐트에 정보를 담아서 payment 액티비티로 넘김
        Intent intent = new Intent(general_menu.this, payment.class);
        intent.putExtra("음식이름", p_food_name); //'음식이름'라는 이름으로 전달
        intent.putExtra("주문개수", p_food_number); // '주문개수'라는 이름으로 전달
        intent.putExtra("주문가격", p_food_price); // '주문가격'라는 이름으로 전달
        startActivity(intent);
    }

    // 옵션에서 로그아웃을 누르면 로그인 화면으로 이동 : 코드분석은 select_case에 작성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(general_menu.this, login.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}






