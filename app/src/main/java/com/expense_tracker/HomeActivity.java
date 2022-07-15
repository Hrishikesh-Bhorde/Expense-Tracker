package com.expense_tracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomnavigationview;
    private FrameLayout framelayout;

    private DashboardFragment dashBoardFragment;
    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Expense Manager");
        setSupportActionBar(toolbar);


        DrawerLayout drawerlayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerlayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        );

        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationview = findViewById(R.id.naView);
        navigationview.setNavigationItemSelectedListener(this);

        dashBoardFragment = new DashboardFragment();
        incomeFragment = new IncomeFragment();
        expenseFragment = new ExpenseFragment();

        setFragment(dashBoardFragment);
        

        bottomnavigationview=findViewById(R.id.bottomnavbar);
        framelayout=findViewById(R.id.main_frame);

        bottomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch(item.getItemId()){

            case R.id.designdashboard:
                setFragment(dashBoardFragment);
                bottomnavigationview.setItemBackgroundResource(R.color.purple_700);
                return true;

            case R.id.expense:
                setFragment(expenseFragment);
                bottomnavigationview.setItemBackgroundResource(R.color.red);
                return true;

            case R.id.income:
                setFragment(incomeFragment);
                bottomnavigationview.setItemBackgroundResource(R.color.green);

                default:
                return false;

            }


            }
        });




    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame,fragment);
        ft.commit();


    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawerlayout = findViewById(R.id.drawer_layout);

        if (drawerlayout.isDrawerOpen(GravityCompat.END))
        {
            drawerlayout.closeDrawer(GravityCompat.END);
        }
        else
        {
            super.onBackPressed();
        }

    }

    public void displaySelectedListner(int itemID){
        Fragment fragment=null;

        switch (itemID){

            case R.id.setting:
                fragment = new DashboardFragment();
                break;

            case R.id.account:
                fragment = new ExpenseFragment();
                break;

            case R.id.logout:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                break;

        }

        if (fragment!=null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame,fragment);
            ft.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListner(item.getItemId());
        return false;
    }
}