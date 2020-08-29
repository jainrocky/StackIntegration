package com.example.rockyjain.stackintegration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_WEB_VIEW;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.CustomModel> {
    List<Question> questions=new ArrayList<>();

    @NonNull
    @Override
    public CustomModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_topics, parent, false);
        return new CustomModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomModel holder, int position) {
        final Question question = questions.get(position);
        holder.title.setText(question.getTitle());
        holder.viewcount.setText(question.getView_count().concat(" Views"));
        Picasso.get().load(question.getOwner_profile_image()).placeholder(R.drawable.default_avatar).into(holder.imageView);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                PopupMenu menu = new PopupMenu(v.getContext(),v);
                menu.inflate(R.menu.popup_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.share_action:
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                                intent.putExtra(Intent.EXTRA_SUBJECT, question.getTitle());
                                intent.putExtra(Intent.EXTRA_TEXT, question.getLink());
                                v.getContext().startActivity(Intent.createChooser(intent, "Share"));

                            // it's not completed yet due to time deficiency :(

                            default:
                                return false;
                        }

                    }
                });
                menu.show();
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), WebViewSearch.class);
                intent.putExtra(REQUEST_WEB_VIEW, question.getLink());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void setAdapter(List<Question> questions){
        this.questions = questions;
        notifyDataSetChanged();
    }

    public class CustomModel extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView viewcount;
        private ImageView imageView;
        public CustomModel(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.tv_question);
            viewcount = itemView.findViewById(R.id.tv_viewcount);
            imageView = itemView.findViewById(R.id.im_avatar);
        }
    }
}
