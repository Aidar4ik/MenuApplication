package com.example.menuapplication.ui.price;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.menuapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PriceDescription extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    //In the future
    private List<DescriptionMode> list=new ArrayList<>();


    public PriceDescription() {

    }

    public static PriceDescription newInstance(String param1) {
        PriceDescription fragment = new PriceDescription();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*        db.collection(mParam1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG",document.getData().toString());
                                list.add(document.toObject(DescriptionMode.class));
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });*/
        return inflater.inflate(R.layout.fragment_price_description, container, false);
    }

    private TextView txtName,txtDesc,txtWeight;
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtName=(TextView)view.findViewById(R.id.desc_name);
        txtDesc=(TextView)view.findViewById(R.id.desc_desc);
        txtWeight=(TextView)view.findViewById(R.id.desc_weight);
    }

    public void getKeyDatabase(String name){
        Log.e("ASDFGgg",name);
        db.collection(name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG",document.getData().toString());
                                list.add(document.toObject(DescriptionMode.class));
                            }
                            if(!(list.get(0).getName()==null
                                    &&list.get(0).getDescription()==null
                                    &&list.get(0).getWeight()==null)){

                                /*txtName.setText(list.get(0).getName());
                                txtDesc.setText(list.get(0).getDescription());
                                txtWeight.setText(list.get(0).getWeight());*/
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}