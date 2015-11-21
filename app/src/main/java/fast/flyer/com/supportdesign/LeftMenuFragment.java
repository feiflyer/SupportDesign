package fast.flyer.com.supportdesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liangchuanfei on 15/11/15.
 */
public class LeftMenuFragment extends Fragment {

    @Bind(R.id.tv_first)
    TextView mTvFirst;
    @Bind(R.id.tv_second)
    TextView mTvSecond;
    @Bind(R.id.tv_third)
    TextView mTvThird;
    @Bind(R.id.tv_fourth)
    TextView mTvFourth;
    @Bind(R.id.tv_fifth)
    TextView mTvFifth;
    @Bind(R.id.tv_sixth)
    TextView mTvSixth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_menu_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_first ,R.id.tv_second , R.id.tv_third ,R.id.tv_fourth ,R.id.tv_fifth,R.id.tv_sixth})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_first:
                Toast.makeText(getActivity() , "第一项被点击了",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
