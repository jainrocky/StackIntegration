package com.example.rockyjain.stackintegration;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class StackRepository {

    private UserDao userDao;
    private List<Users> users;

    public StackRepository(Application application){
        StackDataBase db = StackDataBase.getInstance(application);
        this.userDao = db.userDao();
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    users = userDao.getUser();
                }
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createUser(Users user){
        new CreateUserAsync(userDao).execute(user);
    }
    public void emptyTable(){new DeleteUsersAsycn(userDao).execute(); }

    public List<Users> getUsers() {
        return users;
    }
    private static class CreateUserAsync extends AsyncTask<Users, Void, Void>{
        private UserDao userDao;

        public CreateUserAsync(UserDao userDao){ this.userDao = userDao; }

        @Override
        protected Void doInBackground(Users... users) {
            userDao.createUser(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    private static class DeleteUsersAsycn extends AsyncTask<Void, Void, Void>{
        private UserDao userDao;
        public DeleteUsersAsycn(UserDao userDao){this.userDao = userDao; }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.emptyTable();
            return null;
        }
    }
}
