package fast.flyer.com.supportdesign;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import fast.flyer.com.supportdesign.adapter.RecylerAdapter;

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

        mRecylerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        mRecylerView.setAdapter(new RecylerAdapter(this));
    }

}
