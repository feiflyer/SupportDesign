package fast.flyer.com.supportdesign;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "support_design";

    private android.app.Fragment mFragmentLeft;
    @Bind(R.id.rl_content)
    FrameLayout mContent;
    @Bind(R.id.rl_menu_right)
    FrameLayout mRlMenuRight;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentLeft = getFragmentManager().findFragmentById(R.id.fragment_left);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                Log.i(TAG, "------slideOffset--" + slideOffset);

                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView != null && drawerView.getTag() != null && drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

                    drawerView.setScaleX(leftScale);
                    drawerView.setScaleY(leftScale);
                    drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                    mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                    mContent.setPivotX(0);
                    mContent.setPivotY(mContent.getMeasuredHeight() / 2);
//                    mContent.invalidate();
                    mContent.setScaleX(rightScale);
                    mContent.setScaleY(rightScale);

                } else {

                    mContent.setTranslationX(-drawerView.getMeasuredWidth() * slideOffset);
                    mContent.setPivotX(mContent.getMeasuredWidth());
                    mContent.setPivotY(mContent.getMeasuredHeight() / 2);

//                    mContent.invalidate();

                    mContent.setScaleX(rightScale);
                    mContent.setScaleY(rightScale);

                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

}
