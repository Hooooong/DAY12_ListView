Android Programing
----------------------------------------------------
### 2017.09.19 6일차

#### 공부정리
____________________________________________________

##### __ListView__

- ListView 개념

  ![ListView 이미지](https://github.com/Hooooong/DAY12_ListView/blob/master/image/ListView%20sample.png)

  > ListView는 사용자가 정의한 데이터 목록을 아이템 단위로 구성하여 화면에 출력하는 ViewGroup의 한 종류이다.
  ListView의 아이템들은 세로 방향으로 나열되며, 아이템의 개수가 많아짐에 따라 ListView에 표시될 내용이
  ListView의 크기를 넘어서게 되면 스크롤 기능을 사용해 ListView의 표시 기준 위치를 이동시킬 있다.

- ListView 구조

  ![ListView Adapter 이미지](https://github.com/Hooooong/DAY12_ListView/blob/master/image/ListView.PNG)

- ListView 사용 및 작성법

  ```java
  // 1. ListView 정의
  ListView listView;

  // 2. 데이터를 정의
  List<String> data = new ArrayList<>();

  // 3. 데이터와 ListView 를 연결하는 Adapter 를 생성
  // BaseAdapter 를 상속받아야 한다.
  CustomAdapter customAdapter = new  CustomAdapter(getBaseContext(), data);

  // 4. Adapter 와 ListView 를 연결
  listView.setAdapter(customAdapter);
  ```

- Adapter 작성법

  - Adapter의 흐름

    1. `ListView.setAdapter(adapter)` 를 하면 화면에 보이는 것까지 `getView()` 를 호출하여 데이터에 대한 `Item` 을 보여준다. 보이는 기준은 `Item` 의 1px 까지 걸치면 보여준다.

    2. 사용자가 화면을 위로 스크롤하게 되면 `getVeiw()` 를 호출하여 새로운 `Item` 을 목록에 보여준다.

  - 기본적인 작성법

    ``` java
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      // LayoutInflater 로 xml 파일을 배열 객체로 변환
      // itemView : List item 과 동일;
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

      // TextView 정의(R.layout.list_item 에 정의한 TextView)
      TextView textView = (TextView)itemView.findViewById(R.id.textView);

      textView.setText(data.get(position));

      return itemView;
    }
    ```

    - 매번 `getView()`가 호출되면서 `inflate` 함수를 통해 View를 생성한다. 빠른 스크롤을 할 시에 많은 View 들이 생성이 되기 때문에 메모리낭비가 극심해진다.

    - 성능을 개선시킬 방법은 `View 재활용` 이 있다.

  - View 재활용

    - 스크롤을 할 경우에 `getView()` 를 호출하여 새로운 View 가 생성되는 것인데, 이 때 View 가 있다면 기존에 있던 View 를 사용할 수 있게 한다.

    ```java
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Holder 변수 생성
        Holder holder = null;

        // Item View 를 재사용하기 위해 Null Check 를 한다.
        if (convertView == null) {
            // View 가 없으면 새로 생성
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        }

        // TextView 정의(R.layout.list_item 에 정의한 TextView)
        TextView textView = (TextView)convertView.findViewById(R.id.textView);

        textView.setText(data.get(position));

        return convertView;
    }
    ```

    - 하지만 원하는 View를 찾기 위해서는 `findViewById`를 호출해야 하는데, 매번 `getView()` 를 호출할 때마다 `findViewById` 를 호출하게 되면 자식 View 를 찾기 위해서는 상위 View 부터 순차적으로 탐색을 하기 때문에 View 가 많아질수록 성능에 영향을 주게 된다.

    - 성능을 개선시킬 방법은 `Holder 패턴 사용` 이 있다.

  - Holder 패턴 적용

      ```java
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {

          // Holder 변수 생성
          Holder holder = null;

          // Item View 를 재사용하기 위해 Null Check 를 한다.
          if (convertView == null) {

              convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);

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
      ```
