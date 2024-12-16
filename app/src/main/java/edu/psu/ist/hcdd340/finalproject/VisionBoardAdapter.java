package edu.psu.ist.hcdd340.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VisionBoardAdapter extends RecyclerView.Adapter<VisionBoardAdapter.VisionBoardViewHolder> {

    private List<VisionBoard> visionBoardList;

    public VisionBoardAdapter(List<VisionBoard> visionBoardList) {
        this.visionBoardList = visionBoardList;
    }

    @NonNull
    @Override
    public VisionBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vision_board, parent, false);
        return new VisionBoardViewHolder(view);
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

    static class VisionBoardViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;

        public VisionBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.dynamicText);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}

