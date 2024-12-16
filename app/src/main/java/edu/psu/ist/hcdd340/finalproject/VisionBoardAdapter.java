package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VisionBoardAdapter extends RecyclerView.Adapter<VisionBoardAdapter.VisionBoardViewHolder> {

    public List<VisionBoard> visionBoardList;
    private Context context;

    public VisionBoardAdapter(List<VisionBoard> visionBoardList, Context context) {
        this.visionBoardList = visionBoardList;
        this.context = context;
    }

    @NonNull
    @Override
    public VisionBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vision_board, parent, false);
        return new VisionBoardViewHolder(view, this);
    }


    @Override
    public void onBindViewHolder(@NonNull VisionBoardViewHolder holder, int position) {
        VisionBoard visionBoard = visionBoardList.get(position);
        holder.titleTextView.setText(visionBoard.getVbTitle());
        holder.dateTextView.setText(visionBoard.getDate());
        // Additional bindings as needed
    }

    @Override
    public int getItemCount() {
        return visionBoardList.size();
    }

    class VisionBoardViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView dateTextView;
        final VisionBoardAdapter mAdapter;

        public VisionBoardViewHolder(@NonNull View itemView, VisionBoardAdapter adapter) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.dynamicText);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            this.mAdapter = adapter;

            // Set the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create the Intent to navigate to VisionBoardActivity
                    Intent intent = new Intent(context, VisionBoardActivity.class);
                    // You can pass data to VisionBoardActivity via intent if needed
                    intent.putExtra("item", String.valueOf(visionBoardList.get(getAdapterPosition())));
                    context.startActivity(intent);
                }
            });
        }
    }

}

