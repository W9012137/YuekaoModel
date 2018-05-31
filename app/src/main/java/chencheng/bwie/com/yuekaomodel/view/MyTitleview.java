package chencheng.bwie.com.yuekaomodel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import chencheng.bwie.com.yuekaomodel.R;

public class MyTitleview extends RelativeLayout {

    private EditText editText;
    private Button btn_sousuo;

    public MyTitleview(Context context) {
        this(context,null);
    }

    public MyTitleview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTitleview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View inflate = View.inflate(context, R.layout.shousuo, this);
        editText = inflate.findViewById(R.id.sousuo_ed);
        btn_sousuo = inflate.findViewById(R.id.btn_sousuo);
        btn_sousuo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                listened.toString(editText.getText().toString());
                editText.setText("");
            }
        });

    }
    Listened listened;
    public void setListened(Listened listened){

        this.listened=listened;
    }
    public interface Listened{
        void toString(String editText);
    }
}
