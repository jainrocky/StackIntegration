package com.example.rockyjain.stackintegration;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public class StackViewModel extends AndroidViewModel {
    private StackRepository repository;
    private Users user=null;

    public StackViewModel(@NonNull Application application) {
        super(application);
        repository = new StackRepository(application);
        if (repository.getUsers().size()!=0)
            user = repository.getUsers().get(0);
    }

    public Users getUser() {
        return user;
    }

    public void createUser(Users users){
        repository.emptyTable();
        repository.createUser(users);
    }
}
