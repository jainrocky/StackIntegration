package com.example.rockyjain.stackintegration;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class SelectedChipsAdapter extends RecyclerView.Adapter<SelectedChipsAdapter.ChipsView> {

    List<String> selectedTags;

    @NonNull
    @Override
    public ChipsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chips_view, parent, false);
        return new ChipsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChipsView holder, final int position) {
        holder.tagName.setText(selectedTags.get(position));
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTags.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedTags.size();
    }

    public void setAdapter(List<String> selectedTags){
        this.selectedTags=selectedTags;
        notifyDataSetChanged();
    }

    class ChipsView extends RecyclerView.ViewHolder{
        private TextView tagName;
        private ImageView remove;
        public ChipsView(View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tv_selected_tag);
            remove = itemView.findViewById(R.id.im_remove_tag);
        }
    }
}
