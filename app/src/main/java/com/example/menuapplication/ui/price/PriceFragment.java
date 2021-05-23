package com.example.menuapplication.ui.price;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.content.res.loader.ResourcesProvider;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.menuapplication.R;
import com.example.menuapplication.ui.home.HomeFragment;
import com.example.menuapplication.ui.home.HomeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class PriceFragment extends Fragment {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private static final String ARG_PARAM1 = "key";
    private static String mParam1;

    public PriceFragment() {
        // Required empty public constructor
    }

    public static PriceFragment newInstance(String param1) {
        mParam1=param1;
        PriceFragment fragment = new PriceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    private NavController navController;
    private PriceDescription priceDescription=new PriceDescription();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment_activity_main);
        return inflater.inflate(R.layout.fragment_price, container, false);
    }

    private List<PriceModel> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private PriceAdapter adapter=new PriceAdapter(new PriceAdapter.onItemClickLiesten() {
        @Override
        public void clicker(int position) {
           /* Log.e("ASDFG",list.get(position).getName());
            priceDescription.getKeyDatabase(list.get(position).getName());
            navController.navigate(R.id.priceDescription);*/
        }
    });

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.priceRecycler);
        recyclerView.setAdapter(adapter);
        db.collection(mParam1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.e("TAG",document.getData().toString());
                               list.add(document.toObject(PriceModel.class));
                            }
                            adapter.addList(list);
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}