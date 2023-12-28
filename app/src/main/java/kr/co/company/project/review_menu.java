package kr.co.company.project;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//  리뷰 작성 화면에서의 기능을 작성한 java 파일
public class review_menu extends AppCompatActivity {
    private static final int PERMISSION_CODE = 10;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    // 변수 선언
    select_case sc;
    Button food_menu_button;
    ImageView imageView;
    LinearLayout check;
    TextView check_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_menu);   // review_menu.xml을 액티비티 화면으로 설정
        sc = (select_case) select_case.select_case_act;

        // 위에서 생성해 둔 객체와 id를 통하여 연결
        food_menu_button = (Button)findViewById(R.id.food_menu_button);   // 음식 선택 버튼 변수
        imageView = (ImageView) findViewById(R.id.imageview);   // 후기 사진 이미지를 담을 객체 변수
        check = (LinearLayout) findViewById(R.id.check);   // 체크박스가 있는 뷰그룹
        check_text = (TextView) findViewById(R.id.check_text); // 설문에 대한 질문 텍스트뷰의 변수
        // checkbox 버튼은 라디오버튼이 눌린 후 띄워져야 하므로 처음에는 공간조차 안보이도록 설정
        check.setVisibility(View.GONE);
    }

    // 먹은 음식을 선택하는 버튼 클릭 시
    public void food_menu(View view) {
        // 팝업메뉴 객체 생성
        PopupMenu food_menu = new PopupMenu(this, view);
        // 메뉴 레이아웃 inflat
        food_menu.getMenuInflater().inflate(R.menu.food_menu_item, food_menu.getMenu());
        // 메뉴 아이템 클릭 리스너 달아주기
        food_menu.setOnMenuItemClickListener(
            new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    food_menu_button.setText(item.getTitle());
                    return true;
                }
            }
        );
        food_menu.show(); // 팝업 보여주기
    }

    // 라디오버튼을 선택했을 때 부가적인 설문을 표시하기 위한 부분
    public void radiobutton(View view) {
        // 라디오 버튼이 선택되었는지 확인하는 변수
        boolean radio_check = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            // '만족, '보통' 버튼이 선택된경우 문구 출력 및 체크박스 보이기
            case R.id.yes:case R.id.soso:
                if(radio_check) {
                    check_text.setText("어떤 부분에 만족하셨나요.");
                    check.setVisibility(view.VISIBLE);
                }
                break;
            // '불만족' 버튼이 선택된경우 문구 출력 및 체크박스 보이기
            case R.id.no:
                if(radio_check) {
                    check_text.setText("어떤 부분에 불만족하셨나요.");
                    check.setVisibility(view.VISIBLE);
                }
                break;
        }
    }

    // '사진 첨부하기' 버튼 클릭 시
    public void capture(View v) {
        // 권한 획득
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 카메라에 대한 권한 요청
            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permission, PERMISSION_CODE);
        }

        // 암시적인 인텐트를 사용하여 사진 촬영
        // 이미지를 요청하는 인텐트 생성
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // 카메라 인텐트 실행 / 시작카메라 어플리케이션 사용자 인터페이스 화면 등장
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // 인텐트의 결과를 받아서 화면에 찍은 이미지를 표시
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 정보가 제대로 넘어온경우
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    // '리뷰 등록' 버튼 클릭 시 select_case 액티비티로 전환
    public void review_up(View view) {
        Intent intent = new Intent(review_menu.this, select_case.class);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        // 실행중인 액티비티 종료
        finish();
        sc.finish();
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
            Intent intent = new Intent(review_menu.this, login.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}
