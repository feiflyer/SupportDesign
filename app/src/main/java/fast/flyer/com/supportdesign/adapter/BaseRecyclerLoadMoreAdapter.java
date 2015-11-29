package fast.flyer.com.supportdesign.adapter;

import android.animation.ObjectAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.FileNameMap;
import java.util.List;

import fast.flyer.com.supportdesign.MainActivity;
import fast.flyer.com.supportdesign.R;

/**
 * Created by liangchuanfei on 15/11/29.
 * 通用的RecyclerView适配器
 * 带上拉加载更多功能
 */

/**
 * 需要解决的问题
 * 1、解决刷新时夜加载更多的bug
 * 2、加入footerView实时显示提示的功能（松手刷新等－－）
 * 4、当recyclerView数据不足使，footerView始终显示在底部的功能；
 * 3、如果四网格布局Cell或者瀑布流布局Cell时Fotter没有单独占一行的bug；
 */

public abstract class BaseRecyclerLoadMoreAdapter<T> extends RecyclerView.Adapter<BaseRecyclerLoadMoreAdapter.SparseArrayViewHolder> {

    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView

    private static final int NORMAL_STATUS = 0;//正常状态，加载更多相关不显示
    private static final int PREPARE_LOAD = 1;//加载前提示（如松手加载更多）
    private static final int LOADING_MORE = 2;//正在加载

    private LoadMoreListener mLoadMoreListener;
    protected int layoutId;
    protected RecyclerView mRecyclerView;
    protected List<T> mList;
    private int status;

    public BaseRecyclerLoadMoreAdapter(RecyclerView recyclerView, List<T> list, int layoutId) {
        mList = list;
        this.layoutId = layoutId;
        mRecyclerView = recyclerView;
        initEvents();
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    protected void initEvents() {
        status = NORMAL_STATUS;
        if (mRecyclerView != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        if (linearLayoutManager != null){
                            int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                            Log.i(MainActivity.TAG ,"lastPosition--"+lastPosition);
                            Log.i(MainActivity.TAG ,"getItemCount()-1--"+(getItemCount()-1));
                            if (lastPosition == getItemCount()-1){
                                View view = linearLayoutManager.findViewByPosition(getItemCount() - 1);
                                TextView textView = (TextView) view.findViewById(R.id.tv_load_tips);
                                textView.setVisibility(View.VISIBLE);
                                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation",0 , 360);
                                objectAnimator.setDuration(1500);
                                objectAnimator.start();
                                if(mLoadMoreListener != null) mLoadMoreListener.onLoadMoreStert();
                            }
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                }
            });
        }
    }

    public void loadMoreComplete() {
        status = NORMAL_STATUS;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public BaseRecyclerLoadMoreAdapter.SparseArrayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View footerView = inflateItemView(parent, R.layout.footer_item);
            return new SparseArrayViewHolder(footerView);
        } else {
            View view = inflateItemView(parent, layoutId);
            return new SparseArrayViewHolder(view);
        }
    }

    /**
     * bindDataToItemView是一个抽象方法，延迟到子类实现，
     * bindItemViewClickListener方法设置监听器
     */
    @Override
    public final void onBindViewHolder(BaseRecyclerLoadMoreAdapter.SparseArrayViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            TextView textView = holder.getView(R.id.tv_load_tips);
            textView.setText("footer");
            if (status == NORMAL_STATUS){
                textView.setVisibility(View.INVISIBLE);
            }
        } else {
            final T item = mList.get(position);
            bindDataToItemView(holder, item);
        }
    }

    protected abstract void bindDataToItemView(BaseRecyclerLoadMoreAdapter.SparseArrayViewHolder vh, T item);

    @Override
    public final int getItemCount() {
        return mList == null ? 1 : mList.size()+1;
    }

    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

    /**
     * 通用的ViewHolder
     * 采用SparseArray（Map）维持View的所有集合
     */
    public static class SparseArrayViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> views;

        public SparseArrayViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<View>();
        }

        public <T extends View> T getView(int id) {
            View view = views.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                views.put(id, view);
            }
            return (T) view;
        }

        /**
         * 实现通用ViewHolder的一些属性设置方法，采用链式调用，返回this对象
         */
        public SparseArrayViewHolder setText(int viewId, String value) {
            TextView view = getView(viewId);
            view.setText(value);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setImageResource(int viewId, int imageResId) {
            ImageView view = getView(viewId);
            view.setImageResource(imageResId);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBackgroundResource(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
            View view = getView(viewId);
            view.setOnTouchListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
            View view = getView(viewId);
            view.setOnLongClickListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return SparseArrayViewHolder.this;
        }

    }

    public interface LoadMoreListener {
        void onLoadMoreStert();
    }

}
