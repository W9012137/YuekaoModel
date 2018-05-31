package chencheng.bwie.com.yuekaomodel.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chencheng.bwie.com.yuekaomodel.R;
import chencheng.bwie.com.yuekaomodel.adapter.MyAdapter;
import chencheng.bwie.com.yuekaomodel.bean.CartsBean;
import chencheng.bwie.com.yuekaomodel.bean.DeleteBean;
import chencheng.bwie.com.yuekaomodel.bean.MessageEvent;
import chencheng.bwie.com.yuekaomodel.bean.PriceAndCountEvent;
import chencheng.bwie.com.yuekaomodel.presenter.CartsPresenter;

public class MainActivity extends AppCompatActivity implements ICartsView {

    @BindView(R.id.elv)
    ExpandableListView mElv;
    @BindView(R.id.shopiing_scroll)
    SwipeRefreshLayout mShopiingScroll;
    @BindView(R.id.checkbox2)
    CheckBox mCheckbox2;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_num)

    TextView mTvNum;
    CartsPresenter presenter;
    String uid = "71";
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new CartsPresenter(this);
        presenter.Show(uid);
        EventBus.getDefault().register(this);
        initView();
        mCheckbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.changeAllListCbState(mCheckbox2.isChecked());

            }
        });
        mTvNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainSActivity.class));
            }
        });
    }

    @Override
    public void showList(List<CartsBean.DataBean> groupList, List<List<CartsBean.DataBean.ListBean>> childList, final String uid) {
        adapter = new MyAdapter(MainActivity.this, groupList, childList);
        mElv.setAdapter(adapter);
        mElv.setGroupIndicator(null);
        //默认让其全部展开
        for (int i = 0; i < groupList.size(); i++) {
            mElv.expandGroup(i);
        }
        //删除接口
        adapter.setOnClink(new MyAdapter.OnClinks() {
            @Override
            public void onclikId(int pid) {
                presenter.getdelete(uid, pid + "");
            }

        });
        mShopiingScroll.setColorSchemeColors(Color.GRAY);
        mShopiingScroll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShopiingScroll.setRefreshing(false);
                presenter.Show(uid);
            }
        });

    }

    @Override
    public void showDelete(DeleteBean deleteBean) {
        String code = deleteBean.getCode();
        int i = Integer.parseInt(code);
        if (i == 0) {
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, deleteBean.getMsg(), Toast.LENGTH_LONG).show();
        }

    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {

        mCheckbox2.setChecked(event.isChecked());
    }


    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        mTvNum.setText("结算(" + event.getCount() + ")");
        mTvPrice.setText(event.getPrice() + "");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.Pdstry();
        EventBus.getDefault().unregister(this);


    }

    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mShopiingScroll = (SwipeRefreshLayout) findViewById(R.id.shopiing_scroll);
        mCheckbox2 = (CheckBox) findViewById(R.id.checkbox2);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvNum = (TextView) findViewById(R.id.tv_num);
    }
}
