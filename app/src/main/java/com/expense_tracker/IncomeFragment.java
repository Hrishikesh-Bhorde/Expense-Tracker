package com.expense_tracker;

import  android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expense_tracker.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IncomeFragment extends Fragment {

    //Firebase

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;

    private RecyclerView recyclerview;

    private TextView incomeTotalSum;


 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_income, container, false);

       mAuth=FirebaseAuth.getInstance();
       FirebaseUser mUser = mAuth.getCurrentUser();
       String uid = mUser.getUid();

       mIncomeDatabase = FirebaseDatabase.getInstance("https://expensemanager-6a877-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("IncomeData").child(uid);

       incomeTotalSum = myview.findViewById(R.id.income_txt_result);

       recyclerview = myview.findViewById(R.id.recyclerView_income);

     LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

     layoutManager.setReverseLayout(true);
     layoutManager.setStackFromEnd(true);
     recyclerview.setHasFixedSize(true);
     recyclerview.setLayoutManager(layoutManager);


     mIncomeDatabase.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {

             int totalvalue = 0;

             for (DataSnapshot mysnapshot:snapshot.getChildren()){

              Data data = mysnapshot.getValue(Data.class);

              totalvalue+= data.getAmount();

              String stTotalvalue = String.valueOf(totalvalue);

              incomeTotalSum.setText(stTotalvalue);

          }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });


        return myview;
 }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data,MyViewHolder> adapter=new FirebaseRecyclerAdapter<Data, MyViewHolder>
                (
                        Data.class,
                        R.layout.income_recycler_data,
                        MyViewHolder.class,
                        mIncomeDatabase) {
            @Override
            protected void populateViewHolder(MyViewHolder myViewHolder, Data model, int i) {
                myViewHolder.setType(model.getType());
                myViewHolder.setNote(model.getNote());
                myViewHolder.setDate(model.getDate());
                myViewHolder.setAmount(model.getAmount());

            }
        };

        recyclerview.setAdapter(adapter);
    }
    public static  class MyViewHolder extends RecyclerView.ViewHolder{

     View mView;

 public MyViewHolder(View itemView){
     super(itemView);
     mView = itemView;
 }

         private void setType(String type){

             TextView mType = mView.findViewById(R.id.type_txt_income);
             mType.setText(type+"Sample");
         }

        private void setNote(String note){

            TextView mNote = mView.findViewById(R.id.note_txt_income);
            mNote.setText(note+"Sample");
        }

        private void setDate(String date){

            TextView mDate = mView.findViewById(R.id.date_txt_income);
            mDate.setText(date+"Sample");
        }

        private void setAmount(int amount){

     TextView mAmmount = mView.findViewById(R.id.ammount_txt_income);
     String stammount = String.valueOf(amount);
     mAmmount.setText(stammount);
 }
 }
}