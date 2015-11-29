package fast.flyer.com.supportdesign;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fast.flyer.com.supportdesign.adapter.RecyclerViewLoadMoreAdapter;

public class AppBarLayoutActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.recylerView)
    android.support.v7.widget.RecyclerView mRecylerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout);
        ButterKnife.bind(this);
        mToolbar.setTitle("AppBar");
        setSupportActionBar(mToolbar);

      List<String> mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }

        mRecylerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        mRecylerView.setAdapter(new RecyclerViewLoadMoreAdapter(this ,mDatas ));
    }

}
