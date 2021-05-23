package com.example.menuapplication.ui.likes;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuapplication.R;
import com.example.menuapplication.ui.App;
import com.example.menuapplication.ui.dashboard.RecieptModel;
import com.example.menuapplication.ui.price.PriceModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.ViewHolder> {

    private List<LikesModel> list=new ArrayList<>();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    public void addList(List<LikesModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.likes_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName,txtPrice,txtCount;
        private Button btnAdd;
        private ImageView imgRight,imgLeft;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.likesName);
            txtPrice=itemView.findViewById(R.id.likesPrice);
            txtCount=itemView.findViewById(R.id.count_texter);
            btnAdd=itemView.findViewById(R.id.btnAdded);
            imgRight=itemView.findViewById(R.id.right_imag);
            imgLeft=itemView.findViewById(R.id.left_imag);

            imgRight.setOnClickListener(v -> {
                int temp=Integer.parseInt(txtCount.getText().toString().trim());
                temp++;
                txtCount.setText(String.valueOf(temp));
            });
            txtCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int temp=Integer.parseInt(txtCount.getText().toString().trim());
                    if (temp==0) {
                        btnAdd.setVisibility(View.GONE);
                    }else {
                        btnAdd.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            imgLeft.setOnClickListener(v->{
                int temp=Integer.parseInt(txtCount.getText().toString().trim());
                if (temp!=0) {
                    temp--;
                }
                txtCount.setText(String.valueOf(temp));
            });
            btnAdd.setOnClickListener(v->{
                int temp=Integer.parseInt(txtCount.getText().toString().trim());
                if (temp!=0){
                    App.getDatabase().recieptDao().insert(new RecieptModel(txtName.getText().toString()
                            ,txtPrice.getText().toString()
                            ,txtCount.getText().toString().trim()));

                    Map<String,String> data = new HashMap<>();
                    data.put(txtName.getText().toString().trim()
                            ,txtCount.getText().toString().trim());

                    DocumentReference documentReference=db.collection("reciepts").document();
                    documentReference.set(data);
                    txtCount.setText("0");
                }else {

                }
            });
        }

        public void onBind(LikesModel likesModel) {
            txtName.setText(likesModel.getName());
            txtPrice.setText(likesModel.getPrice());
        }
    }
}
