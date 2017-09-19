package hooooong.com.basiclist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Android Hong on 2017-09-19.
 */
// 2-1. 기본 Adapter 를 상속받아 구현
class CustomAdapter extends BaseAdapter {
    // 데이터 저장소를 Adapter 내부에 저장하는것이 관리하기 편하다.

    Context context;
    List<String> data;

    CustomAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // 현재 데이터의 총 갯수
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // 현재 뿌려질 데이터를 리턴해준다.
        // 호출되는 목록아이템의
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // VIew 의 Id 를 리턴
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 목록에 나타내는 아이템 하나하나를 그려준다.
        // 화면에 보이는 것까지만 호출한다.
        // 보이는 기준은 Item 의 1px 까지 걸치면 보인다.

        // LayoutInflater 로 xml 파일을 배열 객체로 변환
        // itemView : List item 과 동일;

        // Context 를 가져오는 방법은 convertView.getContext(), parent.getContext() 가 있는데
        // convertView.getContext() 는 null 이 넘어올 수 있다.

        // OR
        // 생성자를 통해 넘겨받은 context 를 사용한다.

        // Holder 변수 생성
        Holder holder = null;

        // Item View 를 재사용하기 위해 Null Check 를 한다.
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
            //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

            // View 안에 있는 텍스트뷰 위젯에 값을 입력한다.
            // New 는 되지는 않지만 findViewById 를 통해 TextView 에 메모리가 계속 생성이 된다.
            // 그래서 나온 패턴이 ViewHolder 이다.

            // Holder 생성
            // Item 이 최초 호출될 경우에는 holder 에 위젯들을 담고,
            holder = new Holder(convertView);
            // Holder 를 View 에 붙여놓는다.
            // setTag : View 에게 라벨링을 하기 위해 사용하지만 Holder 자체를 넣어 사용한다.
            convertView.setTag(holder);
        } else {
            // View 에 붙어 있는 Holder 를 가져온다.
            holder = (Holder) convertView.getTag();
        }

        // Holder 의 textView 에 값을 넣는다.
        holder.textView.setText(data.get(position));


        return convertView;
    }

    class Holder {
        TextView textView;

        Holder(View view) {
            textView = (TextView) view.findViewById(R.id.textView);
            setOnClickListener();
        }

        // 화면에 보여지는 View 는
        // 기본적으로 자신이 속한 Component 의 Context 를 그대로 가지고 잇다.
        public void setOnClickListener(){
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(textView.getText());
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);

                    // Intent 에 값을 넘겨주기 위해
                    // putExtra 를 사용한다.

                    intent.putExtra("valueKey", textView.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
