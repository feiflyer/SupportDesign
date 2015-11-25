package fast.flyer.com.supportdesign;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Bind(R.id.rl_content)
    FrameLayout mRlContent;
    @Bind(R.id.flab)
    FloatingActionButton mFlab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.flab)
    public void showSnackBar(){
        //CoordinatorLayout可以用来配合浮动操作按钮的 layout_anchor 和 layout_gravity属性创造出浮动效果，
        //当Snackbar在显示的时候，往往出现在屏幕的底部。为了给Snackbar留出空间，浮动操作按钮需要向上移动。
        Snackbar.make(mRlContent , "snackbar" , Snackbar.LENGTH_LONG).show();
    }

    
}
