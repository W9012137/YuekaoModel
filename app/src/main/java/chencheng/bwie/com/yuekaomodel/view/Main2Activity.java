package chencheng.bwie.com.yuekaomodel.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fynn.fluidlayout.FluidLayout;

import java.util.ArrayList;
import java.util.List;

import chencheng.bwie.com.yuekaomodel.BeanDao;
import chencheng.bwie.com.yuekaomodel.DaoMaster;
import chencheng.bwie.com.yuekaomodel.DaoSession;
import chencheng.bwie.com.yuekaomodel.R;
import chencheng.bwie.com.yuekaomodel.bean.Bean;

public class Main2Activity extends AppCompatActivity {
    private BeanDao beanDao;
    private Button btn_all;
    private MyTitleview main_mytitle;
    private FluidLayout fluidLayout;
    private List<Bean> stringList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper
                (this, "sport-db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        DaoSession daoSession = mDaoMaster.newSession();

        beanDao = daoSession.getBeanDao();
        //找到控件
        btn_all = findViewById(R.id.btn_all);
        main_mytitle = findViewById(R.id.main_mytitle);
        fluidLayout = findViewById(R.id.fluidLayout);
        main_mytitle.setListened(new MyTitleview.Listened() {
            @Override
            public void toString(String editText) {
                if(!TextUtils.isEmpty(editText)){
                    //存入数据库
                    beanDao.insertOrReplaceInTx(new Bean(null,editText));

                    genTag();
                }else{
                    Toast.makeText(Main2Activity.this, "不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //清空按钮
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据库中的所有数据
                beanDao.deleteAll();
                //查询数据库
                genTag();
            }
        });

        genTag();


    }


    private void genTag() {
        List<Bean> beans = beanDao.loadAll();

        stringList.clear();
        stringList.addAll(beans);

        fluidLayout.removeAllViews();

        for (int x=0;x<beans.size();x++){
            TextView tv = new TextView(Main2Activity.this);
            tv.setText(stringList.get(x).getName());
            tv.setTextSize(13);

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(12,12,12,12);

            fluidLayout.addView(tv,params);
        }

    }
}
