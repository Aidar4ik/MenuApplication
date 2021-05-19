package com.example.menuapplication.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuapplication.R;
import com.example.menuapplication.databinding.HomeRecyclerItemBinding;
import com.example.menuapplication.ui.price.PriceModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    HomeRecyclerItemBinding binding;

    private List<HomeModel> list=new ArrayList<>();
    private onItemClickLiesten onItemClickLiesten;

    public HomeAdapter(HomeAdapter.onItemClickLiesten onItemClickLiesten) {
        this.onItemClickLiesten = onItemClickLiesten;
    }

    public void addList(List<HomeModel> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v->{
                onItemClickLiesten.clicker(getAdapterPosition());
            });
        }

        public void onBind(HomeModel homeModel) {
            binding.menuName.setText(homeModel.getName());
        }
    }

    public interface onItemClickLiesten {
        void clicker(int position);
    }
}
