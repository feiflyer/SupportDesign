package fast.flyer.com.supportdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String TAG = "support_design";
    @Bind(R.id.id_nv_menu)
    NavigationView mMenuLeft;
    @Bind(R.id.id_drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.main_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.main_recyler_view)
    RecyclerView mRecylerView;
    @Bind(R.id.main_content)
    FrameLayout mMainContent;
    @Bind(R.id.menu_right)
    NavigationView mMenuRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initEvent();
    }

    private void initViews() {
        mToolbar.setTitle("SupportDesign");
        mToolbar.setSubtitle("左右滑出菜单有惊喜");
        setSupportActionBar(mToolbar);
    }

    private void initEvent() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView != null && drawerView.getTag() != null && drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

//                    drawerView.setScaleX(leftScale);
//                    drawerView.setScaleY(leftScale);

                    //ViewCompat.setScaleX();

                    drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                    mMainContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                    mMainContent.setPivotX(0);
                    mMainContent.setPivotY(mRecylerView.getMeasuredHeight() / 2);
                    mMainContent.setScaleX(rightScale);
                    mMainContent.setScaleY(rightScale);

                } else {

                    mMainContent.setTranslationX(-drawerView.getMeasuredWidth() * slideOffset);
                    mMainContent.setPivotX(mRecylerView.getMeasuredWidth());
                    mMainContent.setPivotY(mRecylerView.getMeasuredHeight() / 2);

                    mMainContent.setScaleX(rightScale);
                    mMainContent.setScaleY(rightScale);

                }
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        //如果不设置导航icon不出来
        drawerToggle.syncState();
        //不要忘了设置
        mDrawerLayout.setDrawerListener(drawerToggle);

        //默认打开右侧菜单栏
        mDrawerLayout.openDrawer(GravityCompat.END);

        mMenuLeft.setNavigationItemSelectedListener(this);

        mMenuRight.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        Log.i(TAG, "----id:" + menuItem.getItemId());
        switch (menuItem.getItemId()) {
            case R.id.toolBar:
                Intent intent = new Intent(MainActivity.this, ToolBarActivity.class);
                startActivity(intent);
                break;

            case R.id.coordinatorLayout:
                Intent coordinatorLayoutIntent = new Intent(MainActivity.this, CoordinatorLayoutActivity.class);
                startActivity(coordinatorLayoutIntent);
                break;
            case R.id.appBarLayout:

                Intent appBarIntent = new Intent(MainActivity.this, AppBarLayoutActivity.class);
                startActivity(appBarIntent);

                break;

            case R.id.collapsingToolbarLayout:

                Intent coolIntent = new Intent(MainActivity.this, CollapsingToolbarLayoutActivity.class);
                startActivity(coolIntent);

                break;

            case R.id.recylerView:

                break;

            case R.id.recylerView_swipeRefreshLayout:

                Intent recyclerViewIntent = new Intent(MainActivity.this, RefreshRecyclerViewActivity.class);
                startActivity(recyclerViewIntent);

                break;

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerLayout.closeDrawer(GravityCompat.END);

        return false;
    }
}
