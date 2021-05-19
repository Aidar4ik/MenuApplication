package com.example.menuapplication.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuapplication.R;
import com.example.menuapplication.databinding.FragmentDashboardBinding;
import com.example.menuapplication.ui.App;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private RecyclerView recyclerView;
    private RecieptAdapter adapter = new RecieptAdapter();
    private List<RecieptModel> list = new ArrayList<>();
    private int summ;
    private int price;
    private int counter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.addAll(App.getDatabase().recieptDao().getAll());
        recyclerView = view.findViewById(R.id.recieptRecycler);
        recyclerView.setAdapter(adapter);
        adapter.addList(list);
        for (int i = 0; i < list.size(); i++) {
            Log.v("TAG",list.get(i).getCount());
            price=Integer.parseInt(list.get(i).getPrice());
            counter=Integer.parseInt(list.get(i).getCount());
            summ+=price*counter;
            binding.allSumm.setText(String.valueOf(summ));
        }
        binding.deleteAll.setOnClickListener(v -> {
            list.clear();
            adapter.addList(list);
            binding.allSumm.setText("");
            App.getDatabase().recieptDao().deleteAll();
        });
    }
}