package com.example.menuapplication.ui.table;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menuapplication.R;
import com.example.menuapplication.databinding.FragmentTableBinding;
import com.example.menuapplication.ui.table.TableModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TableFragment extends Fragment {

    private FragmentTableBinding binding;
    private NavController navController;
    private TableAdapter adapter = new TableAdapter(new TableAdapter.onItemClickLiesten() {
        @Override
        public void clicker(int position) {
            if(list.get(position).getText().equals("Не занято")){
                db.collection("Tables").add(position).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {

                    }
                });
                getActivity().onBackPressed();
            }
        }
    });
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<TableModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        binding = FragmentTableBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db.collection("Tables")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            TableModel tableModel=new TableModel();
                            list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.toObject(TableModel.class));
                            }
                            adapter.addList(list);
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerTable.setAdapter(adapter);
    }
}