package fast.flyer.com.supportdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "support_design";
    @Bind(R.id.id_nv_menu)
    NavigationView mNvMenu;
    @Bind(R.id.id_drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.rl_content)
    RelativeLayout mRlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView != null && drawerView.getTag() != null && drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

//                    drawerView.setScaleX(leftScale);
//                    drawerView.setScaleY(leftScale);
                    drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                    mRlContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                    mRlContent.setPivotX(0);
                    mRlContent.setPivotY(mRlContent.getMeasuredHeight() / 2);
                    mRlContent.setScaleX(rightScale);
                    mRlContent.setScaleY(rightScale);

                } else {

                    mRlContent.setTranslationX(-drawerView.getMeasuredWidth() * slideOffset);
                    mRlContent.setPivotX(mRlContent.getMeasuredWidth());
                    mRlContent.setPivotY(mRlContent.getMeasuredHeight() / 2);

                    mRlContent.setScaleX(rightScale);
                    mRlContent.setScaleY(rightScale);

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

        mNvMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.i(TAG, "----id:" + menuItem.getItemId());
                switch (menuItem.getItemId()) {
                    case R.id.toolBar:
                        Intent intent = new Intent(MainActivity.this , ToolBarActivity.class);
                        startActivity(intent);
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

}
