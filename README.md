Android Programing
----------------------------------------------------
#### 2017.09.19 6일차

###### 예제
____________________________________________________

###### 공부정리
____________________________________________________

  - ListView

      - ListView 란?

          ![ListView 이미지](https://github.com/Hooooong/DAY12_ListView/blob/master/image/ListView%20sample.png)

          > ListView는 사용자가 정의한 데이터 목록을 아이템 단위로 구성하여 화면에 출력하는 ViewGroup의 한 종류이다.
          ListView의 아이템들은 세로 방향으로 나열되며, 아이템의 개수가 많아짐에 따라 ListView에 표시될 내용이
          ListView의 크기를 넘어서게 되면 스크롤 기능을 사용해 ListView의 표시 기준 위치를 이동시킬 있다.

      - ListView 구조

          ![ListView Adapter 이미지](https://github.com/Hooooong/DAY12_ListView/blob/master/image/ListView.PNG)

          - [Spinner](https://github.com/Hooooong/DAY10_Widget) 구조와 동일하게 Adapter를 사용한다.

      - ListView 사용 및 작성법

          - ListView 사용 및 작성법

              ```java
              // 1. ListView 정의
              ListView listView;

              // 2. 데이터를 정의
              List<String> data = new ArrayList<>();

              // 3. 데이터와 ListView 를 연결하는 Adapter 를 생성
              // BaseAdapter 를 상속받아야 한다.
              CustomAdapter customAdapter = new CustomAdapter(getBaseContext(), data);

              // 4. Adapter 와 ListView 를 연결
              listView.setAdapter(customAdapter);
              ```

          - Adapter 작성법

              - 기본적으로 작성하는 것은 쉽지만 getView 메소드를 잘 작성해야 한다.
              - ListView 의 성능을 높힐 수 있는 방법은 getView에서 재사용을 할 수 있게 하고, Holder 패턴을 적용하는 방법이 있다.

              - 기본적인 getView 메소드 작성

                  ``` java
                  @Override
                  public View getView(int position, View convertView, ViewGroup parent) {

                    // 목록에 나타내는 아이템 하나하나를 그려준다.
                    // 화면에 보이는 것까지만 호출한다.
                    // 보이는 기준은 Item 의 1px 까지 걸치면 보인다.

                    // LayoutInflater 로 xml 파일을 배열 객체로 변환
                    // itemView : List item 과 동일;
                    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

                    // TextView 정의(R.layout.list_item 에 정의한 TextView)
                    TextView textView = (TextView)itemView.findViewById(R.id.textView);

                    textView.setText(data.get(position));

                    return convertView;
                  }

                  ```

              - View 재활용하고, Holder를 사용하여 getView 메소드 작성

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
