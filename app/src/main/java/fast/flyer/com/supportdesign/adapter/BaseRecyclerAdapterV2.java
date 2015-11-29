package fast.flyer.com.supportdesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liangchuanfei on 15/11/29.
 * 通用的RecyclerView适配器
 * <p/>
 * 需要实现的功能
 * 1、在RecyclerViewAdapter内部维持一个数据集合的引用
 * ＊2、需要实现Item的点击事件，包括短按和长按，并需要向外部提供设置监听器的方法
 * ＊3、这个适配器必须通用，所以应该是泛型的，
 * 并且viewHolder也是通用的，可以采用SparseArray维持View的所有集合
 * ＊4、在RecyclerViewAdapter内部需要实现数据的绑定（抽象方法，延迟到子类实现），监听器的绑定
 */

public abstract class BaseRecyclerAdapterV2<T, VH extends BaseRecyclerAdapter.SparseArrayViewHolder> extends RecyclerView.Adapter<VH> {
    protected int layoutId;
    protected List<T> mList;
    protected OnitemLongClickListener<T> mOnitemLongClickListener;
    protected OnItemClickListener<T> mOnItemClickListener;

    public BaseRecyclerAdapterV2(List<T> list, int layoutId) {
        mList = list;
        this.layoutId = layoutId;
    }

    private void setOnitemLongClickListener(OnitemLongClickListener<T> onitemLongClickListener) {
        mOnitemLongClickListener = onitemLongClickListener;
    }

    private void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * bindDataToItemView是一个抽象方法，延迟到子类实现，
     * bindItemViewClickListener方法设置监听器
     */
    @Override
    public final void onBindViewHolder(VH holder, int position) {

        final T item = mList.get(position);
        bindDataToItemView(holder, item);

    }

    protected abstract void bindDataToItemView(VH vh, T item);

    @Override
    public final int getItemCount() {
        return mList == null ? 0 : mList.size();
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

    public interface OnItemClickListener<T> {
        void OnItemClickListener(View view, T item);
    }

    public interface OnitemLongClickListener<T> {
        void onItemLongClickListener(View view, T item);
    }
}
