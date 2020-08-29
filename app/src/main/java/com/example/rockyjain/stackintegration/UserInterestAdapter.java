package com.example.rockyjain.stackintegration;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserInterestAdapter extends RecyclerView.Adapter {

    private List<String> tags = new ArrayList<>();
    private List<String> selectedTags = new ArrayList<>();
    private OnTagSelectionNotifier notifier;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_user_interest_selected_tags, parent, false);
            return new TagsRecyclerView(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_user_interest_view, parent, false);
            return new TagsName(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final SelectedChipsAdapter adapter = new SelectedChipsAdapter();
        if(position==0){
            TagsRecyclerView viewHolder= (TagsRecyclerView) holder;
            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder.itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false));
            viewHolder.recyclerView.setAdapter(adapter);
            viewHolder.recyclerView.setHasFixedSize(true);
            viewHolder.recyclerView.setOnFlingListener(null);
            adapter.setAdapter(selectedTags);
        }
        else {
            TagsName h = (TagsName) holder;
            h.tagName.setText(tags.get(position-1));
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedTags.size()<4) {
                        selectedTags.add(tags.get(position - 1));
                        adapter.setAdapter(selectedTags);
                        notifyDataSetChanged();
                        notifier.tagSelectionNotifier(selectedTags);
                    }
                    else
                        Toast.makeText(v.getContext(), "Four Topics Already Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position==0 ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return tags.size()+1;
    }
    public void setAdapter(List<String> tags){
        this.tags=tags;
        notifyDataSetChanged();
    }
    public class TagsName extends RecyclerView.ViewHolder{
        private TextView tagName;
        public TagsName(View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tv_tag_name);
        }
    }

    public class TagsRecyclerView extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        public TagsRecyclerView(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rv_selected_chips);
        }
    }
    void setOnTagSelectionNotifier(OnTagSelectionNotifier notifier){
        this.notifier = notifier;
    }
    interface OnTagSelectionNotifier{
        void tagSelectionNotifier(List<String> tags);
    }
}
