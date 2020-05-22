package com.example.AndroidStudiolab6.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.AndroidStudiolab6.Click;
import com.example.AndroidStudiolab6.Fragment.MainRecyclerFragment;
import com.example.AndroidStudiolab6.Fragment.MainViewPagerFragment;
import com.example.AndroidStudiolab6.R;

public class MainActivity extends FragmentActivity {
    MainRecyclerFragment mainRecyclerFragment;
    MainViewPagerFragment mainViewPagerFragment;
    final static String TAG_1 = "RECYCLER_FRAGMENT";
    final static String TAG_2 = "VIEW_PAGER_FRAGMENT";
    Click click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click = new Click() {
            @Override
            public void click(int position) {
                mainViewPagerFragment = new MainViewPagerFragment(position);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, mainViewPagerFragment, TAG_2);
                transaction.commit();
            }
        };
        mainRecyclerFragment = new MainRecyclerFragment(click);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame,mainRecyclerFragment, TAG_1);
        transaction.commit();
        Button button = findViewById(R.id.cart_btn_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        Button adminButton = findViewById(R.id.admin_btn_main);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag(TAG_2) != null){
            mainRecyclerFragment = new MainRecyclerFragment(click);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, mainRecyclerFragment, TAG_1)
                    .commit();
        } else {
            finish();
        }
    }
}
