package com.example.rockyjain.stackintegration;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class UserInterestActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private static List<String> tags;
    private static UserInterestAdapter adapter;
    private List<String> selectedTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interest);
        mToolBar = findViewById(R.id.toolbar_user_interest);
        setSupportActionBar(mToolBar);
        mRecyclerView = findViewById(R.id.rv_user_interest);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new UserInterestAdapter();
        mRecyclerView.setOnFlingListener(null);
        mRecyclerView.setAdapter(adapter);
        new LoadTagsAsync().execute();

        adapter.setOnTagSelectionNotifier(new UserInterestAdapter.OnTagSelectionNotifier() {
            @Override
            public void tagSelectionNotifier(List<String> tags) {
                selectedTags = tags;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_interest_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_action:
                if(selectedTags.size()==4){
                    StackViewModel viewModel = new StackViewModel(getApplication());
                    Users users = viewModel.getUser();
                    if(users!=null) {
                        users.setTag1(selectedTags.get(0));
                        users.setTag2(selectedTags.get(1));
                        users.setTag3(selectedTags.get(2));
                        users.setTag4(selectedTags.get(3));
                        viewModel.createUser(users);
                    }else
                        Log.i("MyTag", "User NULL");
                    startActivity(new Intent(UserInterestActivity.this, HomeActivity.class));
                    finish();
                }else
                    Toast.makeText(UserInterestActivity.this, "Select Only Four Topics", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    static class LoadTagsAsync extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            tags = DataRequests.getPopularTag();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.setAdapter(tags);
            super.onPostExecute(aVoid);
        }
    }

}
