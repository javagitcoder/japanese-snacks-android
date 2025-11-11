package com.example.snacklearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.snacklearn.R;
import com.example.snacklearn.db.Snack;
import com.example.snacklearn.util.ImageUtil;
import java.util.List;

public class SnackAdapter extends RecyclerView.Adapter<SnackAdapter.ViewHolder> {

    private Context context;
    private List<Snack> snackList;

    public SnackAdapter(Context context, List<Snack> snackList) {
        this.context = context;
        this.snackList = snackList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_snack, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Snack snack = snackList.get(position);

        holder.title.setText(snack.getTitle());
        holder.japanese.setText(snack.getJapanese());
        holder.english.setText(snack.getEnglish());
        holder.description.setText(snack.getDescription());

        // 加载图片
        ImageUtil.loadSnackImage(context, snack.getImageName(), holder.image);
    }

    @Override
    public int getItemCount() {
        return snackList.size();
    }

    public void updateData(List<Snack> newSnackList) {
        this.snackList = newSnackList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView japanese;
        TextView english;
        TextView description;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.snack_image);
            title = itemView.findViewById(R.id.snack_title);
            japanese = itemView.findViewById(R.id.snack_japanese);
            english = itemView.findViewById(R.id.snack_english);
            description = itemView.findViewById(R.id.snack_description);
        }
    }
}