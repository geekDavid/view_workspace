package flowlayout.kinjaz.com.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FlowLayout flowLayout;
    Button text;
    ArrayList<String> list;
    FlowAdapter flowAdapter;
    boolean isShowClose = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowLayout = findViewById(R.id.flowLayout);
        text = findViewById(R.id.text);
        initData();
        //初始化适配器
        flowAdapter = new FlowAdapter() {
            @Override
            public View getView(View convertView, final int position, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_selecter, null);
                    holder = new ViewHolder(convertView);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                final String s = list.get(position);
                holder.text.setText(s);
                if (isShowClose) {
                    holder.imgClose.setVisibility(View.VISIBLE);
                } else {
                    holder.imgClose.setVisibility(View.GONE);
                }
                holder.imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "标签 " + s, Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        notifyDataSetChange();
                    }
                });
                return convertView;
            }

            @Override
            public int getCount() {
                return list.size();
            }

            class ViewHolder {

                TextView text;
                ImageView imgClose;

                public ViewHolder(View itemView) {
                    text = itemView.findViewById(R.id.text);
                    imgClose = itemView.findViewById(R.id.imgClose);
                    itemView.setTag(this);
                }
            }
        };
        //设置适配器
        flowLayout.setAdapter(flowAdapter);
        //item点击事件
        flowLayout.setItemClickLinstener(new FlowLayout.OnItemClickLinstener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(MainActivity.this, "标签 " + position, Toast.LENGTH_SHORT).show();
//                flowLayout.setEnabled(!flowLayout.isClick());
            }
        });
        //item长按事件
        flowLayout.setItemLongClickLinstener(new FlowLayout.OnItemLongClickLinstener() {
            @Override
            public void onItemLongClick(int position, View view) {
                Toast.makeText(MainActivity.this, "长按 " + position, Toast.LENGTH_SHORT).show();
            }
        });
        text.setText("当前状态：" + flowLayout.isClick());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击切换flowLayout是否可以点击
                text.setText("当前状态：" + !flowLayout.isClick());
                flowLayout.setClick(!flowLayout.isClick());
            }
        });

    }

    private void initData() {
        //初始化数据集合
        if (list == null) {
            list = new ArrayList<>();
        }
        list.clear();
        for (int i = 0; i < 30; i++) {
            list.add("标签---" + (i * i));
        }
    }
}
