package com.example.rockyjain.stackintegration;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolBar;
    private StackViewModel mViewModel;
    private static Users user;
    private static List<Question> tagQuestions;
    private OnTagFragmentNotifier tagFragmentNotifier;
    private NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StackViewModel.class);

        //Dummy Authentication
        user = mViewModel.getUser();
        if(user==null || !user.isLoggedIn()) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }else {
            setContentView(R.layout.activity_home);
            mDrawerLayout = findViewById(R.id.main_drawer_layout);
            mTabLayout = findViewById(R.id.tab_layout_home);
            mViewPager = findViewById(R.id.view_pager_home);
            mToolBar = findViewById(R.id.toolbar_home);
            mNavView = findViewById(R.id.nav_view);
            setSupportActionBar(mToolBar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(viewPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setCurrentItem(0);
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mViewPager.setCurrentItem(tab.getPosition());
                    if (tagFragmentNotifier != null)
                        tagFragmentNotifier.tagFragmentNotifier(tagQuestions);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            TextView display = mNavView.getHeaderView(0).findViewById(R.id.tv_dp_name_drawer);
            if (user != null) {
                display.setText(user.getDisplayName());
                Menu menu = mNavView.getMenu();
                menu.add(user.getTag1());
                menu.add(user.getTag2());
                menu.add(user.getTag3());
                menu.add(user.getTag4());

            }
        }
    }

    @Override
    protected void onResume() {
        new TagQuestionLoader(tagQuestions, tagFragmentNotifier).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        super.onResume();
    }

    void setOnTagFragmentNotifier(OnTagFragmentNotifier notifier){
        this.tagFragmentNotifier = notifier;
    }

    interface OnTagFragmentNotifier{
        void tagFragmentNotifier(List<Question> questions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class TagQuestionLoader extends AsyncTask<Void, Void, Void>{
       // private List<Question> tagQuestions;
        private OnTagFragmentNotifier tagFragmentNotifier;

        TagQuestionLoader(List<Question> questions, OnTagFragmentNotifier notifier){
            //this.tagQuestions=questions;
            this.tagFragmentNotifier=notifier;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            tagQuestions = DataRequests.getTagQuestionList(user.getTag1(), user.getTag2(),user.getTag3(), user.getTag4());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(tagFragmentNotifier!=null)
                tagFragmentNotifier.tagFragmentNotifier(tagQuestions);
        }
    }
}
