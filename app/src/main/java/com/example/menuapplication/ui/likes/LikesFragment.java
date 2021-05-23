package com.example.menuapplication.ui.likes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.menuapplication.R;
import com.example.menuapplication.ui.App;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LikesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_likes, container, false);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.addAll(App.getFirestore().likesDao().getAll());
    }

    private RecyclerView recyclerView;
    private LikesAdapter adapter=new LikesAdapter();
    private List<LikesModel> list=new ArrayList<>();
    private ImageView imageView;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.likes_recycler);
        imageView=view.findViewById(R.id.deleteAlles);
        imageView.setOnClickListener(v -> {
            App.getFirestore().likesDao().deleteAll();
            list.clear();
            adapter.addList(list);
        });
        recyclerView.setAdapter(adapter);
        adapter.addList(list);
    }
}