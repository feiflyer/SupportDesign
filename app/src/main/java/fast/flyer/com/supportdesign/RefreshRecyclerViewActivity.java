package fast.flyer.com.supportdesign;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fast.flyer.com.supportdesign.adapter.BaseRecyclerAdapter;
import fast.flyer.com.supportdesign.adapter.RecyclerViewLoadMoreAdapter;

public class RefreshRecyclerViewActivity extends AppCompatActivity {

    //每次添加20条数据
    private static final int INDEX_SPACE = 30;
    @Bind(R.id.recylerView)
    RecyclerView mRecylerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private LinearLayoutManager mLinearLayoutManager;
    private BaseRecyclerAdapter<String> mAdapter;
    private List<String> mDates;

    private int lastVisibleItem;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_view);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        index = 0;
                        mDates.clear();
                        mAdapter.notifyDataSetChanged();

                        for (int i = 0; i < INDEX_SPACE; i++) {
                            mDates.add("数据索引" + (index + i));
                        }

                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        mDates = new ArrayList<>();
        for (int i = 0; i < INDEX_SPACE; i++) {
            mDates.add("数据索引" + (index + i));
        }
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(mLinearLayoutManager);

        //  mAdapter = new RecyclerViewLoadMoreAdapter(this, mDates);
        mAdapter = new BaseRecyclerAdapter<String>(mDates, R.layout.normal_item) {
            @Override
            protected void bindDataToItemView(SparseArrayViewHolder vh, String item) {
                TextView textView = vh.getView(R.id.tv_normal_item);
                textView.setText(item);
            }
        };

        mRecylerView.setAdapter(mAdapter);
        mRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < INDEX_SPACE; i++) {
                                mDates.add("数据索引" + (index + i));
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }, 1500);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

            }
        });
    }

//    这里需要解决的问题：
//    1、给recylerView添加FooterView
//    2、如果四网格布局Cell或者瀑布流布局Cell时Fotter没有单独占一行的bug；


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
