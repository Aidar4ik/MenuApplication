package com.example.menuapplication.ui.price;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuapplication.R;
import com.example.menuapplication.ui.App;
import com.example.menuapplication.ui.dashboard.RecieptModel;
import com.example.menuapplication.ui.likes.LikesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHoler> {


    private List<PriceModel> list=new ArrayList<>();
    private List<ModelForReciept> listSecond=new ArrayList<>();
    private onItemClickLiesten onItemClickLiesten;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    public PriceAdapter(onItemClickLiesten onItemClickLiesten){
        this.onItemClickLiesten=onItemClickLiesten;
    }

    public void addList(List<PriceModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.price_recycler_item,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHoler holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private TextView txtName,txtPrice,txtCount;
        private Button btnAdd;
        private ImageView imgRight,imgLeft,imageLike;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.menuName);
            txtPrice=itemView.findViewById(R.id.menuPrice);
            btnAdd=itemView.findViewById(R.id.btnAdd);
            txtCount=itemView.findViewById(R.id.count_text);
            imgRight=itemView.findViewById(R.id.right_img);
            imgLeft=itemView.findViewById(R.id.left_img);
            imageLike=itemView.findViewById(R.id.addLike);

            imageLike.setOnClickListener(v->{
                App.getFirestore().likesDao().insert(new LikesModel(txtName.getText().toString()
                        ,txtPrice.getText().toString()));
            });

            itemView.setOnClickListener(v->{
                onItemClickLiesten.clicker(getAdapterPosition());
            });

            imgRight.setOnClickListener(v->{
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

        public void onBind(PriceModel priceModel) {
            txtName.setText(priceModel.getName());
            txtPrice.setText(priceModel.getPrice());
        }
    }

    public interface onItemClickLiesten {
        void clicker(int position);
    }
}
