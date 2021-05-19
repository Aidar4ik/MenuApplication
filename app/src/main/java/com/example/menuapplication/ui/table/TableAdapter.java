package com.example.menuapplication.ui.table;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuapplication.R;
import com.example.menuapplication.ui.table.TableAdapter;

import java.util.ArrayList;
import java.util.List;


public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private List<TableModel> list=new ArrayList<>();
    private TableAdapter.onItemClickLiesten onItemClickLiesten;

    public TableAdapter(TableAdapter.onItemClickLiesten onItemClickLiesten) {
        this.onItemClickLiesten = onItemClickLiesten;
    }

    public void addList(List<TableModel> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.table_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName,txtText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.tableName);
            txtText=itemView.findViewById(R.id.waiter);

            itemView.setOnClickListener(v->{
                onItemClickLiesten.clicker(getAdapterPosition());
            });
        }

        public void onBind(TableModel tableModel) {
            txtText.setText(tableModel.getText());
            txtName.setText(tableModel.getCount());
        }
    }

    public interface onItemClickLiesten {
        void clicker(int position);
    }
}
