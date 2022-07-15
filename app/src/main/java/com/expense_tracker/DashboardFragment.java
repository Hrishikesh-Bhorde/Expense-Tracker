package com.expense_tracker;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.expense_tracker.Model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;


public class DashboardFragment extends Fragment {

// floating button

    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;

    private TextView fab_income_txt;
    private TextView fab_expense_txt;

    private boolean isopen = false;

    // Animation clss obj.

    private Animation fadeOpen, fadeClose;

    //FireBase

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_dashboard, container, false);

       mAuth=FirebaseAuth.getInstance();
       System.out.println(mAuth);
       FirebaseUser mUser = mAuth.getCurrentUser();
       String uid = mUser.getUid();
       System.out.println(mUser+uid);
       mIncomeDatabase= FirebaseDatabase.getInstance("https://expensemanager-6a877-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("IncomeData").child(uid);
       mExpenseDatabase= FirebaseDatabase.getInstance("https://expensemanager-6a877-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("ExpenseData").child(uid);


       // Connecting Floating Btns & ViewText to Layout

        fab_main_btn=myview.findViewById(R.id.fb_main_btn);
        fab_income_btn=myview.findViewById(R.id.income_ft_btn);
        fab_expense_btn=myview.findViewById(R.id.expense_ft_btn);

        fab_income_txt=myview.findViewById(R.id.income_ft_text);
        fab_expense_txt=myview.findViewById(R.id.expense_ft_text);

        //Animation

        fadeOpen = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_open);
        fadeClose= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_close);


        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addData();

                if(isopen){
                    fab_income_btn.startAnimation(fadeClose);
                    fab_expense_btn.startAnimation(fadeClose);
                    fab_income_btn.setClickable(false);
                    fab_expense_btn.setClickable(false);

                    fab_income_txt.startAnimation(fadeClose);
                    fab_expense_txt.startAnimation(fadeClose);
                    fab_income_txt.setClickable(false);
                    fab_expense_txt.setClickable(false);
                    isopen=false;

                }
                else{
                    fab_income_btn.startAnimation(fadeOpen);
                    fab_expense_btn.startAnimation(fadeOpen);
                    fab_income_btn.setClickable(true);
                    fab_expense_btn.setClickable(true);

                    fab_income_txt.startAnimation(fadeOpen);
                    fab_expense_txt.startAnimation(fadeOpen);
                    fab_income_txt.setClickable(true);
                    fab_expense_txt.setClickable(true);
                    isopen=true;
                }

            }
        });



        return myview;
    }

    private void addData(){

        // Income ...

        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInsert();

            }
        });

        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expenseDataInsert();

            }
        });


    }

    public void  incomeDataInsert(){

//Alert Dialog obj created as mydialog.
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_to_insert_data,null);
        mydialog.setView(myview);

        final AlertDialog dialog =mydialog.create();

        EditText editAmmount = myview.findViewById(R.id.ammount_edt);
        EditText editType = myview.findViewById(R.id.type_edt);
        EditText editNote = myview.findViewById(R.id.note_edt);

        Button save = myview.findViewById(R.id.save_btn);
        Button cancle = myview.findViewById(R.id.btn_cancle);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = editType.getText().toString().trim();
                String ammount = editAmmount.getText().toString().trim();
                String note = editNote.getText().toString().trim();

                if(TextUtils.isEmpty(type)){
                    editType.setError("Required...");
                    return;
                }

                if(TextUtils.isEmpty(ammount)){
                    editAmmount.setError("Required...");
                    return;
                }

                int ouramt = Integer.parseInt(ammount);

                if(TextUtils.isEmpty(note)){
                    editNote.setError("Required...");
                }

                String id =mIncomeDatabase.push().getKey();
                String mDate= DateFormat.getDateInstance().format(new Date());

                Data data = new Data(ouramt,type,id,note,mDate);

                mIncomeDatabase.child(id).push().setValue(data);

                Toast.makeText(getActivity(),"Data Added..",Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void expenseDataInsert(){

        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_to_insert_data,null);
        mydialog.setView(myview);

         final AlertDialog dialog =mydialog.create();

        EditText ammount = myview.findViewById(R.id.ammount_edt);
        EditText type = myview.findViewById(R.id.type_edt);
        EditText note = myview.findViewById(R.id.note_edt);

        Button save = myview.findViewById(R.id.save_btn);
        Button cancle = myview.findViewById(R.id.btn_cancle);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmAmmount = ammount.getText().toString().trim();
                String tmType = type.getText().toString().trim();
                String tmNote = note.getText().toString().trim();

                if (TextUtils.isEmpty(tmAmmount)){
                    ammount.setError("Required...");
                    return;
                }

                int ourammount = Integer.parseInt(tmAmmount);

                if (TextUtils.isEmpty(tmType)){
                    type.setError("Required...");
                    return;
                }

                if (TextUtils.isEmpty(tmNote)){
                    note.setError("Required...");
                    return;
                }

                String id =mExpenseDatabase.push().getKey();
                String mDate= DateFormat.getDateInstance().format(new Date());

                Data data = new Data(ourammount,tmType,id,tmNote,mDate);

                mExpenseDatabase.child(id).push().setValue(data);

                Toast.makeText(getActivity(),"Data Added..",Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
         dialog.show();
    }
}