package com.example.rockyjain.stackintegration;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "questions")
public class Question {

    @PrimaryKey
    @NonNull
    private String question_id;
    private String link;
    private String title;
    private String owner_profile_image;
    private String view_count;

    Question(){
        owner_profile_image = null;
    }

    public void setQuestion_id(@NonNull String question_id) {
        this.question_id = question_id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwner_profile_image(String owner_profile_image) {
        this.owner_profile_image = owner_profile_image;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    @NonNull
    public String getQuestion_id() {
        return question_id;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner_profile_image() {
        return owner_profile_image;
    }

    public String getView_count() {
        return view_count;
    }
}
