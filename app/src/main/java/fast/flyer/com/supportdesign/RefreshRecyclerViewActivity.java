package fast.flyer.com.supportdesign;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fast.flyer.com.supportdesign.adapter.BaseRecyclerAdapter;
import fast.flyer.com.supportdesign.adapter.BaseRecyclerLoadMoreAdapter;
import fast.flyer.com.supportdesign.adapter.RecyclerViewLoadMoreAdapter;

public class RefreshRecyclerViewActivity extends AppCompatActivity {

    //每次添加数据的条数
    private static final int INDEX_SPACE = 30;
    @Bind(R.id.recylerView)
    RecyclerView mRecylerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private LinearLayoutManager mLinearLayoutManager;
    private BaseRecyclerLoadMoreAdapter<String> mAdapter;
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
                        for (int i = 0; i < INDEX_SPACE; i++) {
                            mDates.add("数据索引" + (index + i));
                        }
                        index += INDEX_SPACE;
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
        index += INDEX_SPACE;
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(mLinearLayoutManager);

        //  mAdapter = new RecyclerViewLoadMoreAdapter(this, mDates);
        mAdapter = new BaseRecyclerLoadMoreAdapter<String>(mRecylerView , mDates, R.layout.normal_item) {
            @Override
            protected void bindDataToItemView(SparseArrayViewHolder vh, String item) {
                TextView textView = vh.getView(R.id.tv_normal_item);
                textView.setText(item);
            }
        };

        mAdapter.setLoadMoreListener(new BaseRecyclerLoadMoreAdapter.LoadMoreListener() {
            @Override
            public void onLoadMoreStert() {
                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < INDEX_SPACE; i++) {
                                mDates.add("数据索引" + (index + i));
                            }
                            index += INDEX_SPACE;
                            mAdapter.loadMoreComplete();
                        }
                    }, 1500);
            }
        });

        mRecylerView.setAdapter(mAdapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
