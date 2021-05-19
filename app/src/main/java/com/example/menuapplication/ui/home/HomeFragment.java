package com.example.menuapplication.ui.home;

import android.app.ActionBar;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuapplication.MainActivity;
import com.example.menuapplication.R;
import com.example.menuapplication.databinding.FragmentHomeBinding;
import com.example.menuapplication.ui.price.PriceFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private NavController navController;

    private HomeAdapter adapter=new HomeAdapter(new HomeAdapter.onItemClickLiesten() {
        @Override
        public void clicker(int position) {
            navController.navigate(R.id.navigation_price);
            PriceFragment.newInstance(list.get(position).getName());
        }
    });
    private List<HomeModel> list=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db.collection("category")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.toObject(HomeModel.class));
                    }adapter.addList(list);
                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.homeRecycler.setAdapter(adapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}