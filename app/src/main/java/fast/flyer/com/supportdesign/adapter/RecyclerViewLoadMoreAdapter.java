package fast.flyer.com.supportdesign.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fast.flyer.com.supportdesign.R;
import fast.flyer.com.supportdesign.ToolBarActivity;

/**
 * Created by liangchuanfei on 15/11/28.
 * 使用RecylerView上拉加载更多的adapter
 */
public class RecyclerViewLoadMoreAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView
    private Context mContext;
    private List<String> mDatas;

    public RecyclerViewLoadMoreAdapter(Context context, List<String> list) {
        mContext = context;
        mDatas = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.normal_item, parent, false);
            return new ItemViewHolder(itemView);
        } else {
            View footerView = LayoutInflater.from(mContext).inflate(R.layout.footer_item, parent, false);
            return new ItemViewHolder(footerView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            try {
                ((ItemViewHolder) holder).tv_normal_item.setText(mDatas.get(position));
            } catch (Exception e) {

            }
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).tv_load_tips.setText("上拉加载更多");
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size() + 1;
        }
        return 1;
    }

    /**
     * 判断时显示普通布局，还是显示Footer布局
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tv_normal_item;

        public ItemViewHolder(View view) {
            super(view);
            tv_normal_item = (TextView) view.findViewById(R.id.tv_normal_item);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_load_tips;

        public FooterViewHolder(View footerView) {
            super(footerView);
            tv_load_tips = (TextView) footerView.findViewById(R.id.tv_load_tips);
        }
    }

}
