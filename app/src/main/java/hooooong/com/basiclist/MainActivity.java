package hooooong.com.basiclist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 1. ListView 정의
    ListView listView;

    // 2. 데이터를 정의
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        // 2-1. 데이터를 임의로 넣는다
        for(int i = 0 ; i<100; i++){
            data.add("임시값 : " +i);
        }

        // 3. 데이터와 ListView 를 연결하는 Adapter 를 생성
        // BaseAdapter 를 상속받아야 한다.
        CustomAdapter customAdapter = new CustomAdapter(getBaseContext(), data);
        // 4. Adapter 와 ListView 를 연결
        listView.setAdapter(customAdapter);
    }

    public void initView(){
        listView = (ListView)findViewById(R.id.listView);
    }
}
