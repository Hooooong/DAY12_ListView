package hooooong.com.basiclist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. Intent 를 통해 값 꺼내오기
        // MainActivity 를 통해 넘어온 Intent 를 꺼내온다.
        Intent intent = getIntent();
        /*
        // Intent 에서 값의 묶음인 번들을 꺼내고
        Bundle bundle = intent.getExtras();
        // Bundle 에서 값을 Key로 꺼내온다.
        String result = bundle.getString("valueKey");
        */
        // Intent 에서 Bundle 을 거치지 않고 바로 꺼내온다.
        String result = intent.getStringExtra("valueKey");

        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText(result);
    }
}
