package com.telstra.androidexercise.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.telstra.androidexercise.R;
import com.telstra.androidexercise.base.BaseActivity;
import com.telstra.androidexercise.base.BaseApplication;


public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new ListFragment()).commit();

    }

    @Override
    public int getContainerId() {
        return 0;
    }
}